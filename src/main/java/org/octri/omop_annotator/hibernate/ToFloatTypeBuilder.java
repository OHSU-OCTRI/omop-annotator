package org.octri.omop_annotator.hibernate;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.FloatTypeDescriptor;
import org.hibernate.type.descriptor.sql.NumericTypeDescriptor;

/**
 * This custom type builder can be used to convert a field in a Hibernate entity to a Java Float. It supports Oracle
 * Float and Postgres Numeric. The out of the box Hibernate types expect Postgres Numeric to be a java BigDecimal.
 */
public class ToFloatTypeBuilder {

    private static final String NAME = "ToFloat";

    private static final String ORACLE_DATABASE = "oracle";
    private static final String POSTGRES_DATABASE = "postgres";

    public static final AbstractSingleColumnStandardBasicType<Float> ORACLE_INSTANCE = new ToFloatTypeBuilder(
            ORACLE_DATABASE).build();
    public static final AbstractSingleColumnStandardBasicType<Float> POSTGRES_INSTANCE = new ToFloatTypeBuilder(
            POSTGRES_DATABASE).build();

    String database;

    private ToFloatTypeBuilder(String database) {
        this.database = database;
    }

    private AbstractSingleColumnStandardBasicType<Float> build() {
        if (database.equals(POSTGRES_DATABASE)) {
            return new PostgresFloatType();
        } else if (database.equals(ORACLE_DATABASE)) {
            return new OracleFloatType();
        } else {
            throw new IllegalArgumentException("Database " + database + " not supported");
        }

    }

    private class OracleFloatType extends AbstractSingleColumnStandardBasicType<Float> {

        @Override
        public String getName() {
            return NAME;
        }

        public OracleFloatType() {
            super(FloatTypeDescriptor.INSTANCE, FloatFloatJavaDescriptor.INSTANCE);
        }

    }

    private class PostgresFloatType extends AbstractSingleColumnStandardBasicType<Float> {

        @Override
        public String getName() {
            return NAME;
        }

        public PostgresFloatType() {
            super(NumericTypeDescriptor.INSTANCE, BigDecimalFloatJavaDescriptor.INSTANCE);
        }
    }

}
