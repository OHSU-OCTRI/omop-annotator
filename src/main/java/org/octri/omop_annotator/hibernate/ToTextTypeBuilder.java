package org.octri.omop_annotator.hibernate;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.StringTypeDescriptor;
import org.hibernate.type.descriptor.sql.ClobTypeDescriptor;
import org.hibernate.type.descriptor.sql.LongVarcharTypeDescriptor;

/**
 * This custom type builder can be used to convert a field in the database to a Java String. If the sql field is a
 * String, it goes through the normal Hibernate mapping. If the field is a Clob, the out of the box Hibernate descriptor
 * requires the @Lob annotation conflicting with other db implementations, so this will convert them to a String without
 * requiring the annotation.
 */
public class ToTextTypeBuilder {

    private static final String NAME = "ToText";

    private static final String DEFAULT_DATABASE = "default";
    private static final String ORACLE_DATABASE = "oracle";

    public static final AbstractSingleColumnStandardBasicType<String> DEFAULT_INSTANCE = new ToTextTypeBuilder(
            DEFAULT_DATABASE).build();
    public static final AbstractSingleColumnStandardBasicType<String> ORACLE_INSTANCE = new ToTextTypeBuilder(
            ORACLE_DATABASE).build();

    String database;

    private ToTextTypeBuilder(String database) {
        this.database = database;
    }

    private AbstractSingleColumnStandardBasicType<String> build() {
        if (database.equals(ORACLE_DATABASE)) {
            return new OracleTextType();
        } else {
            return new DefaultTextType();
        }

    }

    // Use the same Hibernate transformation given by the TextType class
    private class DefaultTextType extends AbstractSingleColumnStandardBasicType<String> {

        @Override
        public String getName() {
            return NAME;
        }

        public DefaultTextType() {
            super(LongVarcharTypeDescriptor.INSTANCE, StringTypeDescriptor.INSTANCE);
        }
    }

    // Map CLOBs to the built-in StringTypeDescriptor that can already handle them
    private class OracleTextType extends AbstractSingleColumnStandardBasicType<String> {

        @Override
        public String getName() {
            return NAME;
        }

        public OracleTextType() {
            super(ClobTypeDescriptor.DEFAULT, StringTypeDescriptor.INSTANCE);
        }

    }

}
