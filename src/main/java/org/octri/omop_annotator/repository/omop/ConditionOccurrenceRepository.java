package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.ConditionOccurrence;
import org.octri.omop_annotator.view.ConditionOccurrenceRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "condition_occurrence")
public interface ConditionOccurrenceRepository extends PagingAndSortingRepository<ConditionOccurrence, Long> {

	static final String conditionOccurrenceQuery = "select condition_occurrence_id as id, person_id as person, condition_start_datetime as conditionStart, condition_end_datetime as conditionEnd, condition_concept.concept_name as condition, condition_type_concept.concept_name as conditionType, visit_occurrence_id as visitOccurrence"
			+ " from condition_occurrence"
			+ " left join concept condition_concept on condition_concept.concept_id = condition_occurrence.condition_concept_id"
			+ " left join concept condition_type_concept on condition_type_concept.concept_id = condition_occurrence.condition_type_concept_id"
			+ " where condition_occurrence.person_id = ?1";

	List<ConditionOccurrence> findByPersonId(Long personId);

	// TODO: OA-95 ; Eliminate native queries for distribution to other organizations.
	@Query(value = conditionOccurrenceQuery, nativeQuery = true)
	List<ConditionOccurrenceRow> conditionOccurrenceRows(Long personId);
}
