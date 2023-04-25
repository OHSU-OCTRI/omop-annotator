package org.octri.omop_annotator.view;

/**
 * The context needed for merging two pools
 */
public class MergePoolsDTO {

    private Long mergePoolId;
    private Long destinationPoolId;

    public Long getMergePoolId() {
        return mergePoolId;
    }

    public void setMergePoolId(Long mergePoolId) {
        this.mergePoolId = mergePoolId;
    }

    public Long getDestinationPoolId() {
        return destinationPoolId;
    }

    public void setDestinationPoolId(Long destinationPoolId) {
        this.destinationPoolId = destinationPoolId;
    }

}
