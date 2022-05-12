package org.octri.omop_annotator.hibernate;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.service.ServiceRegistry;

public class ToFloatTypeContributor implements TypeContributor {

    String dialect;

    public ToFloatTypeContributor(String dialect) {
        this.dialect = dialect;
    }

    // TODO: Read the deprecation below on contributeType and determine approach for Hibernate 6
    @Override
    public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        if (isPostgres(dialect)) {
            typeContributions.contributeType(ToFloatType.POSTGRES_INSTANCE, "ToFloat");
        } else if (isOracle(dialect)) {
            typeContributions.contributeType(ToFloatType.ORACLE_INSTANCE, "ToFloat");
        } else {
            throw new NotImplementedException("Cannot instantiate the dialect " + dialect);
        }
    }

    private boolean isOracle(String dialect) {
        return dialect.toLowerCase().contains("oracle");
    }

    private boolean isPostgres(String dialect) {
        return dialect.toLowerCase().contains("postgres");
    }

}
