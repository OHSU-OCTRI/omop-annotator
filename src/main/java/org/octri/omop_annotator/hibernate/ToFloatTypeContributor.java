package org.octri.omop_annotator.hibernate;

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
        } else {
            typeContributions.contributeType(ToFloatTypeBuilder.DEFAULT_INSTANCE);
        }
    }

}
