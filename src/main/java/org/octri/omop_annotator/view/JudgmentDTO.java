package org.octri.omop_annotator.view;

import java.util.List;

import org.octri.omop_annotator.domain.app.AnnotationLabel;
import org.octri.omop_annotator.domain.app.Judgment;
import org.springframework.util.Assert;

/**
 * {@link Judgment} representation for working with JSON and front end code.
 */
public class JudgmentDTO {

    private Long id;
    private Long userId;
    private Long poolEntryId;
    private Long annotationLabelId;
    private List<AnnotationLabel> annotationLabels;

    public JudgmentDTO() {
    }

    /**
     * Initialize the DTO from a Judgment object.
     * 
     * @param judgment
     */
    public void initialize(Judgment judgment) {
        Assert.notNull(judgment, "Judgment is required");
        this.id = judgment.getId();
        this.userId = judgment.getUser().getId();
        this.poolEntryId = judgment.getPoolEntry().getId();
        if (judgment.getAnnotationLabel() != null) {
            this.annotationLabelId = judgment.getAnnotationLabel().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPoolEntryId() {
        return poolEntryId;
    }

    public void setPoolEntryId(Long poolEntryId) {
        this.poolEntryId = poolEntryId;
    }

    public Long getAnnotationLabelId() {
        return annotationLabelId;
    }

    public void setAnnotationLabelId(Long annotationLabelId) {
        this.annotationLabelId = annotationLabelId;
    }

    public List<AnnotationLabel> getAnnotationLabels() {
        return annotationLabels;
    }

    public void setAnnotationLabels(List<AnnotationLabel> annotationLabels) {
        this.annotationLabels = annotationLabels;
    }

}
