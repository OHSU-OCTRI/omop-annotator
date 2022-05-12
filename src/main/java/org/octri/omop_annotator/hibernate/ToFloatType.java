package org.octri.omop_annotator.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

/**
 * This custom type can be used to convert a field in a Hibernate entity to a Java Float. It supports Oracle Float and
 * Postgres Numeric. The out of the box Hibernate types expect Postgres Numeric to be a java BigDecimal.
 */
public class ToFloatType implements UserType, DynamicParameterizedType {

    private static final String ORACLE_DATABASE = "oracle";
    private static final String POSTGRES_DATABASE = "postgres";

    public static final ToFloatType ORACLE_INSTANCE = new ToFloatType(ORACLE_DATABASE);
    public static final ToFloatType POSTGRES_INSTANCE = new ToFloatType(POSTGRES_DATABASE);

    String database;

    public ToFloatType() {
        // TODO: May be able to remove this once we remove the DynamicParamterizedType
    }

    public ToFloatType(String database) {
        this.database = database;
    }

    @Override
    public int[] sqlTypes() {
        if (database.equals(POSTGRES_DATABASE)) {
            return new int[] { Types.NUMERIC };
        } else if (database.equals(ORACLE_DATABASE)) {
            return new int[] { Types.FLOAT };
        }

        throw new IllegalArgumentException("Database " + database + " not supported");
    }

    @Override
    public Class returnedClass() {
        return Float.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        // For some reason, if we directly call getFloat and the value is null, it is set to 0.0.
        byte[] bytes = rs.getBytes(names[0]);
        if (bytes == null) {
            return null;
        }
        return rs.getFloat(names[0]);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        throw new NotImplementedException("This is a read-only database.");
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        throw new NotImplementedException("This is a read-only database.");
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        throw new NotImplementedException("This is a read-only database.");
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        throw new NotImplementedException("This is a read-only database.");
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        throw new NotImplementedException("This is a read-only database.");
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;

        if (Objects.isNull(x) || Objects.isNull(y))
            return false;

        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public void setParameterValues(Properties parameters) {
        this.database = parameters.getProperty("database");
    }
}