package org.octri.omop_annotator.view;

/**
 * A summary of the validation performed before merging two pools
 * 
 * @author yateam
 *
 */
public interface MergeValidationSummary {

	public Long getTopicNumber();

	public Integer getDocumentId();

}
