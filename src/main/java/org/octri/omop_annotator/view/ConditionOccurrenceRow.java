package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Projection Interface which defines a row in the ConditionList table (Vue component).
 */
public interface ConditionOccurrenceRow {

	public Long getId();

	public Long getPersonId();

	public String getCondition();

	public String getConditionType();

	public Date getConditionStart();

	public Date getConditionEnd();

	public Long getVisitOccurrence();

}
