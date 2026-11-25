package org.octri.omop_annotator.view;

import java.time.LocalDateTime;

/**
 * Projection Interface defining a row and details in the GroupedList component
 */
public interface DrugExposureRow {

	public Integer getId();

	public Integer getPersonId();

	public String getDrug();

	public String getDrugType();

	public LocalDateTime getDrugStart();

	public LocalDateTime getDrugEnd();

	public String getStopReason();

	public Integer getRefills();

	public Float getQuantity();

	public Integer getDaysSupply();

	public String getDrugSource();

	public String getDrugSourceValue();

	public String getRouteSourceValue();

	public String getDoseUnitSourceValue();

	public Integer getVisitOccurrence();

}
