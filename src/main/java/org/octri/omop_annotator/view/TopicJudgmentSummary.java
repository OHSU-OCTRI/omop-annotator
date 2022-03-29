package org.octri.omop_annotator.view;

/**
 * A summary of a topic, and its related pool entries and judgments
 * 
 * @author yateam
 *
 */
public interface TopicJudgmentSummary {

	public Long getTopicId();

	public Integer getTopicNumber();

	public String getNarrative();
	
	public Integer getNumEntries();

	public Integer getCompletedJudgments();

}
