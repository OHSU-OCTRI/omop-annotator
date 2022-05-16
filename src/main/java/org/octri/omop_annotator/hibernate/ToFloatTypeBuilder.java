package org.octri.omop_annotator.hibernate;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.NumericTypeDescriptor;

/**
 * This custom type builder can be used to convert a field in the database to a Java Float. If the sql field is a
 * Float, it goes through the normal Hibernate mapping. If the field is Numeric, the out of the box Hibernate descriptor
 * converts the field to be a BigDecimal or BigInteger, so this will convert them to a Float instead.
 */
public class ToFloatTypeBuilder {

    private static final String NAME = "ToFloat";

    private static final String DEFAULT_DATABASE = "default";
    private static final String POSTGRES_DATABASE = "postgres";

    public static final AbstractSingleColumnStandardBasicType<Float> DEFAULT_INSTANCE = new ToFloatTypeBuilder(
            DEFAULT_DATABASE).build();
    public static final AbstractSingleColumnStandardBasicType<Float> POSTGRES_INSTANCE = new ToFloatTypeBuilder(
            POSTGRES_DATABASE).build();

    String database;

    private ToFloatTypeBuilder(String database) {
        this.database = database;
    }

    private AbstractSingleColumnStandardBasicType<Float> build() {
        if (database.equals(POSTGRES_DATABASE)) {
            return new PostgresFloatType();
        } else {
            return new DefaultFloatType();
        }
    }

    // Use the same Hibernate transformation given by the FloatType class
    private class DefaultFloatType extends AbstractSingleColumnStandardBasicType<Float> {

        @Override
        public String getName() {
            return NAME;
        }

        public DefaultFloatType() {
            super(org.hibernate.type.descriptor.sql.FloatTypeDescriptor.INSTANCE,
                    org.hibernate.type.descriptor.java.FloatTypeDescriptor.INSTANCE);
        }

    }

    // Use a custom Java descriptor
    private class PostgresFloatType extends AbstractSingleColumnStandardBasicType<Float> {

        @Override
        public String getName() {
            return NAME;
        }

        public PostgresFloatType() {
            super(NumericTypeDescriptor.INSTANCE, NumericFloatJavaDescriptor.INSTANCE);
        }

    }

}
