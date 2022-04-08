package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the Visit table.
 * 
 */
public interface VisitOccurrenceRow {

	public Long getId();

	public Long getPerson();

	public String getVisitType();

	public Date getVisitStart();

	public Date getVisitEnd();

	public String getProviderName();

	public String getCareSiteName();

}
