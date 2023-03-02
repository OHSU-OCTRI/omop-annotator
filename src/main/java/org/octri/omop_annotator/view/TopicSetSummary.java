package org.octri.omop_annotator.view;

/**
 * A summary of the pools in a topic set and the judgments completed per user
 */
public interface TopicSetSummary {

    public Long getPoolId();

    public String getPoolName();

    public Long getTopicId();

    public Integer getTopicNumber();

    public String getTopicNarrative();

    public Integer getPoolSize();

    public String getJudge();

    public Integer getCompleted();

}
