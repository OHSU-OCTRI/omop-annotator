package org.octri.omop_annotator.view;

import java.time.LocalDate;
import java.util.List;

import org.octri.omop_annotator.domain.app.Judgment;
import org.octri.omop_annotator.domain.app.Pin;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private String pins;

    private ExportedJudgmentRow() {
    }

    /**
     * Construct a ExportedJudgmentRow from a given Judgment.
     *
     * @param refreshDate
     * @param judgment
     * @return
     * @throws JsonProcessingException
     */
    public static ExportedJudgmentRow fromJudgment(LocalDate refreshDate, Judgment judgment, @Nullable List<Pin> pins,
            ObjectMapper pinExportMapper) {
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
        if (pins != null) {
            try {
                row.pins = pinExportMapper.writeValueAsString(pins);
            } catch (JsonProcessingException e) {
                row.pins = "Error serializing pins";
            }
        }
        return row;
    }

    public static final String[] FIELDS = { "refreshDate",
            "username",
            "poolName",
            "topicSetName",
            "topicNumber",
            "documentId",
            "annotationLabel",
            "outputLabel",
            "pins" };

    public String[] toCsvRow() {
        String[] row = { getRefreshDate().toString(),
                getUsername(),
                getPoolName(),
                getTopicSetName(),
                getTopicNumber().toString(),
                getDocumentId().toString(),
                getAnnotationLabel(),
                getOutputLabel(),
                getPins() };
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

    public String getPins() {
        return pins;
    }

}
