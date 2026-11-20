package org.octri.omop_annotator.domain.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.octri.omop_annotator.SpringConfiguredJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;

import tools.jackson.databind.json.JsonMapper;

/**
 * Unit tests for {@link SearchIndexJob}
 */
@SpringConfiguredJsonMapper
public class SearchIndexJobTest {

    @Autowired
    private JsonMapper mapper;

    @Test
    void testGetDuration() throws ParseException {
        var job = new SearchIndexJob();
        var started = mapper.convertValue("2023-03-16 17:54:35", LocalDateTime.class);
        var ended = mapper.convertValue("2023-03-16 17:56:26", LocalDateTime.class);

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
