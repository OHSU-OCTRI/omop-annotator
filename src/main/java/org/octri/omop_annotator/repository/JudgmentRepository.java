package org.octri.omop_annotator.repository;

import org.octri.omop_annotator.domain.Judgment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "judgment")
public interface JudgmentRepository extends PagingAndSortingRepository<Judgment, Long> {
}