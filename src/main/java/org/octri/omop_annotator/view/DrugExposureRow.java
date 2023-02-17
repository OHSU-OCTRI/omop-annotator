package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Projection Interface defining a row and details in the GroupedList component
 */
public interface DrugExposureRow {

	public Integer getId();

	public Integer getPersonId();

	public String getDrug();

	public String getDrugType();

	public Date getDrugStart();

	public Date getDrugEnd();

	public String getStopReason();

	public Integer getQuantity();

	public Integer getVisitOccurrence();

}
