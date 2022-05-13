package org.octri.omop_annotator.hibernate;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

/**
 * This descriptor is a passthrough for when Hibernate interprets the database field as a String and the entity field is
 * also a String.
 */
public class StringStringJavaDescriptor extends AbstractTypeDescriptor<String> {

    public static final StringStringJavaDescriptor INSTANCE = new StringStringJavaDescriptor();

    @SuppressWarnings("unchecked")
    public StringStringJavaDescriptor() {
        super(String.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public String fromString(String string) {
        return string;
    }

    @Override
    public <X> X unwrap(String value, Class<X> type, WrapperOptions options) {
        throw new NotImplementedException("This is a read-only database");
    }

    @Override
    public <X> String wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
