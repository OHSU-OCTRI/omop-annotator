package org.octri.omop_annotator.repository.app;

import java.util.List;

import org.octri.omop_annotator.domain.app.Pool;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pool")
public interface PoolRepository extends PagingAndSortingRepository<Pool, Long> {

	Long countByAnnotationSchemaId(Long id);

	List<Pool> findByTopicSetId(Long id);
}
