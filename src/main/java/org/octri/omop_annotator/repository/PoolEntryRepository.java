package org.octri.omop_annotator.repository;

import org.octri.omop_annotator.domain.PoolEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pool_entry")
public interface PoolEntryRepository extends PagingAndSortingRepository<PoolEntry, Long> {
}