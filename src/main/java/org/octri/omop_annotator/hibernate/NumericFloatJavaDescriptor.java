package org.octri.omop_annotator.hibernate;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

/**
 * This descriptor handles transformation when Hibernate interprets the database field as a java.math.BigDecimal or
 * java.math.BigInteger but the entity is typed as a Float.
 */
public class NumericFloatJavaDescriptor extends AbstractTypeDescriptor<Float> {

    public static final NumericFloatJavaDescriptor INSTANCE = new NumericFloatJavaDescriptor();

    @SuppressWarnings("unchecked")
    public NumericFloatJavaDescriptor() {
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
        if (BigDecimal.class.isInstance(value)) {
            return ((BigDecimal) value).floatValue();
        }
        if (BigInteger.class.isInstance(value)) {
            return ((BigInteger) value).floatValue();
        } else if (String.class.isInstance(value)) {
            return Float.valueOf(((String) value));
        }
        throw unknownWrap(value.getClass());
    }

}
