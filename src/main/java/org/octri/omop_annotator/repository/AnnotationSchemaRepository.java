package org.octri.omop_annotator.repository;

import org.octri.omop_annotator.domain.AnnotationSchema;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "annotation_schema")
public interface AnnotationSchemaRepository extends PagingAndSortingRepository<AnnotationSchema, Long> {
}
