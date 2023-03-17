package org.octri.omop_annotator.repository.app;

import java.util.Optional;

import org.octri.omop_annotator.domain.app.SearchIndexJob;
import org.octri.omop_annotator.domain.app.SearchIndexJob.JobStatus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "search_index_job")
public interface SearchIndexJobRepository extends PagingAndSortingRepository<SearchIndexJob, Long> {

    Optional<SearchIndexJob> findByStatus(JobStatus started);
}