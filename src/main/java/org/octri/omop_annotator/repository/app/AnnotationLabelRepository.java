package org.octri.omop_annotator.repository.app;

import java.util.List;

import org.octri.omop_annotator.domain.app.AnnotationLabel;
import org.octri.omop_annotator.domain.app.AnnotationSchema;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = "annotation_label")
public interface AnnotationLabelRepository extends ListCrudRepository<AnnotationLabel, Long> {

	List<AnnotationLabel> findByAnnotationSchemaId(Long annotationSchemaId);

	@Transactional
	void deleteByAnnotationSchema(AnnotationSchema schema);
}
