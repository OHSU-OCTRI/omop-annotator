package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the Observations table.
 */
public interface ObservationRow {

    public Integer getId();

    public Integer getPerson();

    public String getObservation();

    public String getSourceValue();

    public Date getDate();

    public String getType();

    public String getValue();

    public String getValueAsConcept();

    public Integer getVisitOccurrence();
}
