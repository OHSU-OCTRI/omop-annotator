package org.octri.omop_annotator.hibernate;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.service.ServiceRegistry;

public class ToTextTypeContributor extends OmopTypeContributor {

    public ToTextTypeContributor(String dialect) {
        super(dialect);
    }

    @Override
    public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        if (isOracle(getDialect())) {
            typeContributions.contributeType(ToTextTypeBuilder.ORACLE_INSTANCE);
        } else {
            typeContributions.contributeType(ToTextTypeBuilder.DEFAULT_INSTANCE);
        }
    }
}
