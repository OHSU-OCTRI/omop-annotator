package org.octri.omop_annotator.view;

/**
 * A summary of a pool entry and its judgment by the user
 * 
 * @author yateam
 *
 */
public interface PoolEntryJudgmentSummary {

	public Long getPoolEntryId();

	public Integer getDocumentId();

	public Integer getSortOrder();
	
	public Long getJudgmentId();

	public String getAnnotation();

}
