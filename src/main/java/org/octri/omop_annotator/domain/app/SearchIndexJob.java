package org.octri.omop_annotator.domain.app;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.octri.authentication.server.security.entity.User;
import org.springframework.util.Assert;

/**
 * Represents a job that is initiated to perform full reindexing for search. Provides details regarding which user
 * started the indexing, at what time, how long it took to complete, and the job's current status.
 */
@Entity
public class SearchIndexJob extends AbstractEntity {

    public enum JobStatus {
        STARTED, COMPLETED, FAILED
    }

    @ManyToOne
    @NotNull
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endedAt;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private JobStatus status;

    /**
     * Empty Constructor
     */
    public SearchIndexJob() {
    }

    /**
     * Constructs a new job associated with the given user that is started now.
     */
    public SearchIndexJob(User user) {
        this.user = user;
    }

    /**
     * Start the job.
     *
     * @param description
     *            - job description
     * @return this
     */
    public SearchIndexJob start(String description) {
        Assert.isNull(this.startedAt, "Job has already started");
        this.startedAt = new Date();
        this.status = JobStatus.STARTED;
        this.description = description;
        return this;
    }

    /**
     * Set the this job to failed.
     *
     * @return
     */
    public SearchIndexJob fail() {
        Assert.notNull(this.startedAt, "Job has not started");
        Assert.isNull(this.endedAt, "Job has already ended.");
        this.endedAt = new Date();
        this.status = JobStatus.FAILED;
        return this;
    }

    /**
     * Set the job to completed.
     *
     * @return
     */
    public SearchIndexJob complete() {
        Assert.notNull(this.startedAt, "Job has not started");
        Assert.isNull(this.endedAt, "Job has already ended");
        this.endedAt = new Date();
        this.status = JobStatus.COMPLETED;
        return this;
    }

    /**
     * Computes the job duration.
     *
     * @return total time in hours, minutes, and seconds.
     */
    public String getDuration() {
        if (startedAt != null && endedAt != null) {
            long ms = Math.abs(endedAt.getTime() - startedAt.getTime());
            Duration duration = Duration.ofMillis(ms);

            long hours = duration.toHours();
            int minutes = duration.toMinutesPart();
            int seconds = duration.toSecondsPart();
            return String.format("%02d hr %02d min %02d sec", hours, minutes, seconds);
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Date endedAt) {
        this.endedAt = endedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

}
