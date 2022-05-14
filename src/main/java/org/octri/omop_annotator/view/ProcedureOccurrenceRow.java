package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the procedures table.
 */
public interface ProcedureOccurrenceRow {

    public Integer getId();

    public Integer getPersonId();

    public String getProcedure();

    public String getProcedureType();

    public Date getDate();

    public Integer getQuantity();

    public Integer getVisitOccurrence();
}
