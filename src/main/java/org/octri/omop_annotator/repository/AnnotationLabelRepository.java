package org.octri.omop_annotator.repository;

import org.octri.omop_annotator.domain.AnnotationLabel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "annotation_label")
public interface AnnotationLabelRepository extends PagingAndSortingRepository<AnnotationLabel, Long> {
}