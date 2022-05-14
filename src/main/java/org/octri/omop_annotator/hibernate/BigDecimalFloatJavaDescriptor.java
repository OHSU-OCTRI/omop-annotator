package org.octri.omop_annotator.hibernate;

import java.math.BigDecimal;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

/**
 * This descriptor handles transformation when Hibernate interprets the database field as a java.math.BigDecimal but the
 * entity is typed as a Float.
 */
public class BigDecimalFloatJavaDescriptor extends AbstractTypeDescriptor<Float> {

    public static final BigDecimalFloatJavaDescriptor INSTANCE = new BigDecimalFloatJavaDescriptor();

    @SuppressWarnings("unchecked")
    public BigDecimalFloatJavaDescriptor() {
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

        BigDecimal d = (BigDecimal) value;
        return Float.valueOf(d.floatValue());
    }

}
