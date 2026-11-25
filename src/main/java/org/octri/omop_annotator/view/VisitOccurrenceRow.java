package org.octri.omop_annotator.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Row in the Visit table.
 *
 */
public interface VisitOccurrenceRow {

	public Integer getId();

	public Integer getPerson();

	public String getVisitType();

	public String getVisitSource();

	public String getVisitSourceValue();

	public LocalDateTime getVisitStart();

	public LocalDateTime getVisitEnd();

	public String getProviderName();

	public String getCareSiteName();

	public String getAdmittingSourceValue();

	public String getDischargeToSourceValue();

	/**
	 * The date serialization format is a configurable application property. This method provides a consistent format
	 * for front end javascript code.
	 *
	 * @return ISO date (ex. "2023-01-13")
	 */
	public default String getVisitStartIsoDate() {
		if (this.getVisitStart() != null) {
			var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return this.getVisitStart().format(formatter);
		}
		return null;
	}
}
