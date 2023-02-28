package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the Observations table.
 */
public interface ObservationRow {

    public Integer getId();

    public Integer getPerson();

    public String getObservation();

    public String getObservationSource();

    public String getObservationSourceValue();

    public Date getDate();

    public String getObservationType();

    public String getValueAsString();

    public Float getValueAsNumber();

    public String getValueAsConcept();

    public String getObservationQualifier();

    public String getQualifierSourceValue();

    public String getObservationUnit();

    public String getUnitSourceValue();

    public Integer getVisitOccurrence();
}
