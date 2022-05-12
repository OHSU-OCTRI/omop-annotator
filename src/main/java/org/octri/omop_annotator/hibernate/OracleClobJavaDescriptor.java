package org.octri.omop_annotator.hibernate;

import java.sql.Clob;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

public class OracleClobJavaDescriptor extends AbstractTypeDescriptor<String> {

    private static final Log log = LogFactory.getLog(OracleClobJavaDescriptor.class);
    public static final OracleClobJavaDescriptor INSTANCE = new OracleClobJavaDescriptor();

    public OracleClobJavaDescriptor() {
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

        Clob clob = (Clob) value;
        try {
            return clob.getSubString(1, (int) clob.length());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

}
