package org.octri.omop_annotator.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The result of the attempted merge of pools
 */
public class MergePoolsResult implements Serializable {

    private Long mergePoolId;
    private Long destinationPoolId;
    private boolean successful;
    private List<String> errors = new ArrayList<>();

    public MergePoolsResult(MergePoolsDTO mergePoolsDTO) {
        this.mergePoolId = mergePoolsDTO.getMergePoolId();
        this.destinationPoolId = mergePoolsDTO.getDestinationPoolId();
    }

    public Long getMergePoolId() {
        return mergePoolId;
    }

    public Long getDestinationPoolId() {
        return destinationPoolId;
    }

    public boolean getSuccessful() {
        return getErrors().isEmpty();
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }

}
