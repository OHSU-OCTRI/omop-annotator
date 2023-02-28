package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Projection Interface defining a Condition row in the VisitRelatedList table (Vue component).
 */
public interface ConditionOccurrenceRow {

	public Integer getId();

	public Integer getPersonId();

	public String getCondition();

	public String getConditionType();

	public Date getConditionStart();

	public Date getConditionEnd();

	public String getConditionSource();

	public String getConditionSourceValue();

	public String getConditionStatus();

	public String getConditionStatusSourceValue();

	public String getStopReason();

	public Integer getVisitOccurrence();

}
