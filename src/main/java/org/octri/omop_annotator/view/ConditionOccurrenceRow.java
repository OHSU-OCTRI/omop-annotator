package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the ConditionList table.
 * 
 */
public interface ConditionOccurrenceRow {

	public Long getId();

	public Long getPerson();

	public String getCondition();

	public String getConditionType();

	public Date getConditionStart();

	public Date getConditionEnd();

	public Long getVisitOccurrence();

}
