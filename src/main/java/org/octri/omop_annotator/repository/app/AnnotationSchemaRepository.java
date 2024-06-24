package org.octri.omop_annotator.repository.app;

import org.octri.omop_annotator.domain.app.AnnotationSchema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "annotation_schema")
public interface AnnotationSchemaRepository extends CrudRepository<AnnotationSchema, Long> {
}
