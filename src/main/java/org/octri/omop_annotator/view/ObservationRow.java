package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the Observations table.
 */
public interface ObservationRow {

    public Integer getId();

    public Integer getPerson();

    public String getName();

    public Date getDate();

    public String getType();

    public String getValue();

    public Integer getVisitOccurrence();
}
