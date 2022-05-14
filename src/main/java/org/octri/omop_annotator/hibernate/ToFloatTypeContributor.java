package org.octri.omop_annotator.hibernate;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.service.ServiceRegistry;

public class ToFloatTypeContributor extends OmopTypeContributor {

    public ToFloatTypeContributor(String dialect) {
        super(dialect);
    }

    @Override
    public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        if (isPostgres(getDialect())) {
            typeContributions.contributeType(ToFloatTypeBuilder.POSTGRES_INSTANCE);
        } else if (isOracle(getDialect())) {
            typeContributions.contributeType(ToFloatTypeBuilder.ORACLE_INSTANCE);
        } else {
            throw new NotImplementedException("Cannot instantiate the dialect " + getDialect());
        }
    }

}
