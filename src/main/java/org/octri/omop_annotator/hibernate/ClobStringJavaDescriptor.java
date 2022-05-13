package org.octri.omop_annotator.hibernate;

import java.sql.Clob;
import java.sql.SQLException;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

/**
 * This descriptor handles transformation when Hibernate interprets the database field as a java.sql.Clob but the entity
 * is typed as a String with no @Lob annotation.
 */
public class ClobStringJavaDescriptor extends AbstractTypeDescriptor<String> {

    private static final Log log = LogFactory.getLog(ClobStringJavaDescriptor.class);
    public static final ClobStringJavaDescriptor INSTANCE = new ClobStringJavaDescriptor();

    @SuppressWarnings("unchecked")
    public ClobStringJavaDescriptor() {
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

        Clob clob = (Clob) value;
        try {
            return clob.getSubString(1, (int) clob.length());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

}
