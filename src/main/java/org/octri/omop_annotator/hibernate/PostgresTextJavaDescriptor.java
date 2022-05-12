package org.octri.omop_annotator.hibernate;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

public class PostgresTextJavaDescriptor extends AbstractTypeDescriptor<String> {

    public static final PostgresTextJavaDescriptor INSTANCE = new PostgresTextJavaDescriptor();

    public PostgresTextJavaDescriptor() {
        super(String.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public String fromString(String string) {
        return string;
    }

    @Override
    public <X> X unwrap(String value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        return (X) value;
    }

    @Override
    public <X> String wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
