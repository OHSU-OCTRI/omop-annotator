package org.octri.omop_annotator.hibernate;

import org.hibernate.boot.model.TypeContributor;

public abstract class OmopTypeContributor implements TypeContributor {

    private String dialect;

    public OmopTypeContributor(String dialect) {
        this.dialect = dialect;
    }

    public String getDialect() {
        return dialect;
    }

    public boolean isOracle(String dialect) {
        return dialect.toLowerCase().contains("oracle");
    }

    public boolean isPostgres(String dialect) {
        return dialect.toLowerCase().contains("postgres");
    }

}
