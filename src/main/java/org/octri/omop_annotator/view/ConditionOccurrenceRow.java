package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Projection Interface which defines a row in the ConditionList table (Vue component).
 */
public interface ConditionOccurrenceRow {

	public Integer getId();

	public Integer getPersonId();

	public String getCondition();

	public String getConditionType();

	public Date getConditionStart();

	public Date getConditionEnd();

	public Integer getVisitOccurrence();

}
