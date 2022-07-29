package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the Visit table.
 * 
 */
public interface VisitOccurrenceRow {

	public Integer getId();

	public Integer getPerson();

	public String getVisitType();

	public String getVisitSourceValue();

	public Date getVisitStart();

	public Date getVisitEnd();

	public String getProviderName();

	public String getCareSiteName();

}
