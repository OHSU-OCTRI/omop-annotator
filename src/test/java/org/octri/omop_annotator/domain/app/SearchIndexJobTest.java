package org.octri.omop_annotator.domain.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link SearchIndexJob}
 */
public class SearchIndexJobTest {

    @Test
    void testGetDuration() throws ParseException {
        var job = new SearchIndexJob();
        var format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        var started = format.parse("2023-03-16 17:54:35");
        var ended = format.parse("2023-03-16 17:56:26");

        assertNull(job.getDuration());

        job.setStartedAt(started);
        job.setEndedAt(ended);

        assertEquals("00 hr 01 min 51 sec", job.getDuration());
    }

    @Test
    void testStart() {
        var job = new SearchIndexJob();
        job.start("Testing");
        assertNotNull(job.getStartedAt());
        assertEquals(SearchIndexJob.JobStatus.STARTED, job.getStatus());
    }

    @Test
    void testComplete() {
        var job = new SearchIndexJob();
        job.start("Testing");
        job.complete();
        assertNotNull(job.getEndedAt());
        assertEquals(SearchIndexJob.JobStatus.COMPLETED, job.getStatus());
    }

    @Test
    void testCompleteWithoutStarting() {
        var job = new SearchIndexJob();
        assertThrows(IllegalArgumentException.class, () -> {
            job.complete();
        });
    }

    @Test
    void testCompleteAlreadyCompleted() {
        var job = new SearchIndexJob();
        job.start("Testing");
        job.complete();
        assertThrows(IllegalArgumentException.class, () -> {
            job.complete();
        });
    }

    @Test
    void testFailWithoutStarting() {
        var job = new SearchIndexJob();
        assertThrows(IllegalArgumentException.class, () -> {
            job.fail();
        });
    }

    @Test
    void testFail() {
        var job = new SearchIndexJob();
        job.start("Testing");
        job.fail();
        assertNotNull(job.getEndedAt());
        assertEquals(SearchIndexJob.JobStatus.FAILED, job.getStatus());
    }

}
