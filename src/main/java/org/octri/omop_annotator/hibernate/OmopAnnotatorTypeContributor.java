package org.octri.omop_annotator.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.descriptor.java.BigDecimalJavaType;

public class OmopAnnotatorTypeContributor implements TypeContributor {

    private static final Log log = LogFactory.getLog(OmopAnnotatorTypeContributor.class);

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
            log.info("Dialect is PostgreSQL, injecting custom type");
            typeContributions.getTypeConfiguration().getJavaTypeRegistry().addBaselineDescriptor(Float.class,
                    BigDecimalJavaType.INSTANCE);
        }
    }

}
