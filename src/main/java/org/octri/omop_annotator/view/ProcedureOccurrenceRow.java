package org.octri.omop_annotator.view;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Row in the procedures table.
 */
public interface ProcedureOccurrenceRow {

    public Long getId();

    public Long getPersonId();

    public String getProcedure();

    public String getProcedureType();

    public Date getDate();

    public BigDecimal getQuantity();

    public Long getVisitOccurrence();
}
