package org.octri.omop_annotator.view;

import java.time.LocalDate;

import org.octri.omop_annotator.domain.app.Judgment;
import org.springframework.util.Assert;

/**
 * Row for exporting judgments from the system.
 */
public class ExportedJudgmentRow {

    private LocalDate refreshDate;
    private String username;
    private String poolName;
    private String topicSetName;
    private Integer topicNumber;
    private Integer documentId;
    private String annotationLabel;
    private String outputLabel;

    private ExportedJudgmentRow() {
    }

    /**
     * Construct a ExportedJudgmentRow from a given Judgment.
     *
     * @param refreshDate
     * @param judgment
     * @return
     */
    public static ExportedJudgmentRow fromJudgment(LocalDate refreshDate, Judgment judgment) {
        Assert.notNull(refreshDate, "Refresh date is required");
        Assert.notNull(judgment, "Judgment is required");

        var poolEntry = judgment.getPoolEntry();
        var row = new ExportedJudgmentRow();

        row.refreshDate = refreshDate;
        row.username = judgment.getUser().getUsername();
        row.poolName = poolEntry.getPool().getName();
        row.topicSetName = poolEntry.getTopic().getTopicSet().getName();
        row.topicNumber = poolEntry.getTopic().getTopicNumber();

        row.documentId = judgment.getPoolEntry().getDocumentId();
        row.annotationLabel = judgment.getAnnotationLabel().getDisplayLabel();
        row.outputLabel = judgment.getAnnotationLabel().getOutputLabel();
        return row;
    }

    public static final String[] FIELDS = { "refreshDate",
            "username",
            "poolName",
            "topicSetName",
            "topicNumber",
            "documentId",
            "annotationLabel",
            "outputLabel" };

    public String[] toCsvRow() {
        String[] row = { getRefreshDate().toString(),
                getUsername(),
                getPoolName(),
                getTopicSetName(),
                getTopicNumber().toString(),
                getDocumentId().toString(),
                getAnnotationLabel(),
                getOutputLabel() };
        return row;
    }

    public LocalDate getRefreshDate() {
        return refreshDate;
    }

    public String getUsername() {
        return username;
    }

    public String getPoolName() {
        return poolName;
    }

    public String getTopicSetName() {
        return topicSetName;
    }

    public Integer getTopicNumber() {
        return topicNumber;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public String getAnnotationLabel() {
        return annotationLabel;
    }

    public String getOutputLabel() {
        return outputLabel;
    }

}
