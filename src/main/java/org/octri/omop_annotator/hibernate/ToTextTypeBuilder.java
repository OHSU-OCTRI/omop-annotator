package org.octri.omop_annotator.hibernate;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.ClobTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

/**
 * This custom type builder can be used to convert a text field in a Hibernate entity to a Java String. It supports
 * Oracle Clob and Postgres Text. The out of the box Hibernate types require the @Lob annotation for Oracle.
 */
public class ToTextTypeBuilder {

    private static final String NAME = "ToText";

    private static final String ORACLE_DATABASE = "oracle";
    private static final String POSTGRES_DATABASE = "postgres";

    public static final AbstractSingleColumnStandardBasicType<String> ORACLE_INSTANCE = new ToTextTypeBuilder(
            ORACLE_DATABASE).build();
    public static final AbstractSingleColumnStandardBasicType<String> POSTGRES_INSTANCE = new ToTextTypeBuilder(
            POSTGRES_DATABASE).build();

    String database;

    private ToTextTypeBuilder(String database) {
        this.database = database;
    }

    private AbstractSingleColumnStandardBasicType<String> build() {
        if (database.equals(POSTGRES_DATABASE)) {
            return new PostgresTextType();
        } else if (database.equals(ORACLE_DATABASE)) {
            return new OracleTextType();
        } else {
            throw new IllegalArgumentException("Database " + database + " not supported");
        }

    }

    private class OracleTextType extends AbstractSingleColumnStandardBasicType<String> {

        @Override
        public String getName() {
            return NAME;
        }

        public OracleTextType() {
            super(ClobTypeDescriptor.DEFAULT, ClobStringJavaDescriptor.INSTANCE);
        }

    }

    private class PostgresTextType extends AbstractSingleColumnStandardBasicType<String> {

        @Override
        public String getName() {
            return NAME;
        }

        public PostgresTextType() {
            super(VarcharTypeDescriptor.INSTANCE, StringStringJavaDescriptor.INSTANCE);
        }
    }

}
