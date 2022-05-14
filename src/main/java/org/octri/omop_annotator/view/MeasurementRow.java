package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the Measurement List table.
 * 
 */
public interface MeasurementRow {

	public Integer getId();

	public Integer getPerson();

	public Date getMeasurementDatetime();

	public String getMeasurement();

	public String getMeasurementType();

	public String getValueSourceValue();

	public Float getValueAsNumber();

	public String getValueAsConcept();

	public String getUnit();

	public Integer getVisitOccurrence();

}