package org.octri.omop_annotator.view;

/**
 * A summary of the pool and the judgments completed per user
 */
public interface PoolSummary {

    public Long getPoolId();

    public String getPoolName();

    public Long getTopicId();

    public Integer getTopicNumber();

    public String getTopicNarrative();

    public Integer getPoolSize();

    public String getJudge();

    public Integer getCompleted();

    public static String[] CSV_FIELDS = new String[] { "topic_number", "topic_narrative", "judge", "completed",
            "pool_size" };

    public default String[] toCsvRow() {
        return new String[] {
                getTopicNumber().toString(),
                getTopicNarrative(),
                getJudge(),
                getCompleted() == null ? "0" : getCompleted().toString(),
                getPoolSize().toString() };
    }

}
