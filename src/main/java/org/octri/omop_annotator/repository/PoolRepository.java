package org.octri.omop_annotator.repository;

import org.octri.omop_annotator.domain.Pool;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pool")
public interface PoolRepository extends PagingAndSortingRepository<Pool, Long> {
}
