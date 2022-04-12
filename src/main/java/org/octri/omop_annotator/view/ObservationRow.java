package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the Observations table.
 */
public interface ObservationRow {

    public Long getId();

    public Long getPerson();

    public String getName();

    public Date getDate();

    public String getType();

    public String getValue();

    public Long getVisitOccurrence();
}
