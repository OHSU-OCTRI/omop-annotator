package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.ConditionOccurrence;
import org.octri.omop_annotator.view.ConditionOccurrenceRow;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "condition_occurrence")
public interface ConditionOccurrenceRepository extends PagingAndSortingRepository<ConditionOccurrence, Long> {

	List<ConditionOccurrenceRow> findByPersonId(Long personId);
}
