package org.octri.omop_annotator.hibernate;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.service.ServiceRegistry;

public class ToTextTypeContributor implements TypeContributor {

    String dialect;

    public ToTextTypeContributor(String dialect) {
        this.dialect = dialect;
    }

    @Override
    public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        if (isPostgres(dialect)) {
            typeContributions.contributeType(ToTextTypeBuilder.POSTGRES_INSTANCE);
        } else if (isOracle(dialect)) {
            typeContributions.contributeType(ToTextTypeBuilder.ORACLE_INSTANCE);
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
