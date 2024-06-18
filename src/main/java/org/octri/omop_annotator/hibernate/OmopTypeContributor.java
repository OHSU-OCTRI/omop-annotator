package org.octri.omop_annotator.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.java.BigDecimalJavaType;

/**
 * Overrides the Hibernate Java type descriptor registered for Java {@link Float} fields when the dialect of the OMOP
 * database is PostgreSQL. Fixes a schema validation exception cause by PostgreSQL sing a {@link SqlTypes.NUMERIC}
 * column for OMOP columns where other databases use {@link SqlTypes.FLOAT}.
 */
public class OmopTypeContributor implements TypeContributor {

    private static final Log log = LogFactory.getLog(OmopTypeContributor.class);

    @Override
    public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        log.debug("Running custom type contributor");
        var jdbcServices = serviceRegistry.getService(JdbcServices.class);
        if (jdbcServices == null) {
            log.debug("jdbcServices not found, exiting type contributor");
            return;
        }

        var dialect = jdbcServices.getDialect();
        if (dialect instanceof PostgreSQLDialect) {
            log.info("Dialect is PostgreSQL, injecting custom types");
            typeContributions.getTypeConfiguration().getJavaTypeRegistry().addBaselineDescriptor(Float.class,
                    BigDecimalJavaType.INSTANCE);
        }
    }

}
