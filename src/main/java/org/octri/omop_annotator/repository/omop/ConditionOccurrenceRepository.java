package org.octri.omop_annotator.repository.omop;

import org.octri.omop_annotator.domain.omop.ConditionOccurrence;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "condition_occurrence")
public interface ConditionOccurrenceRepository extends PagingAndSortingRepository<ConditionOccurrence, Long> {
}
