package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the Measurement List table.
 * 
 */
public interface MeasurementRow {

	public Long getId();

	public Long getPerson();

	public Date getMeasurementDatetime();

	public String getMeasurement();

	public String getMeasurementType();

	public Float getValueAsNumber();

	public String getValueAsConcept();

	public String getUnit();

	public Long getVisitOccurrence();

}