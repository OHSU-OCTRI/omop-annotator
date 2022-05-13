package org.octri.omop_annotator.hibernate;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

/**
 * This descriptor is a passthrough for when Hibernate interprets the database field as a Float and the entity field is
 * also a Float.
 */
public class FloatFloatJavaDescriptor extends AbstractTypeDescriptor<Float> {

    public static final FloatFloatJavaDescriptor INSTANCE = new FloatFloatJavaDescriptor();

    @SuppressWarnings("unchecked")
    public FloatFloatJavaDescriptor() {
        super(Float.class, ImmutableMutabilityPlan.INSTANCE);
    }

    @Override
    public Float fromString(String string) {
        return Float.valueOf(string);
    }

    @Override
    public <X> X unwrap(Float value, Class<X> type, WrapperOptions options) {
        throw new NotImplementedException("This is a read-only database");
    }

    @Override
    public <X> Float wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        return (Float) value;
    }

}
