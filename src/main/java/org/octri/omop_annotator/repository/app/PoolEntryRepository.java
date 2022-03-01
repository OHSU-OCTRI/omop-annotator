package org.octri.omop_annotator.repository.app;

import org.octri.omop_annotator.domain.app.PoolEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pool_entry")
public interface PoolEntryRepository extends PagingAndSortingRepository<PoolEntry, Long> {
}
