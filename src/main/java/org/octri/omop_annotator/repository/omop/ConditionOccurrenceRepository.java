package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.ConditionOccurrence;
import org.octri.omop_annotator.view.ConditionOccurrenceRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "condition_occurrence")
public interface ConditionOccurrenceRepository extends PagingAndSortingRepository<ConditionOccurrence, Integer> {

	static final String query = "select co.id as id, co.person.id as personId, condition.name as condition,"
			+ " conditionType.name as conditionType, co.conditionStart as conditionStart,"
			+ " co.conditionEnd as conditionEnd, visitOccurrence.id as visitOccurrence"
			+ " from ConditionOccurrence co"
			+ " left join co.condition condition"
			+ " left join co.conditionType conditionType"
			+ " left join co.visitOccurrence visitOccurrence";

	@Query(query + " where co.person.id = ?1")
	List<ConditionOccurrenceRow> findByPersonId(Integer personId);

	@Query(query + " where visitOccurrence.id = ?1")
	List<ConditionOccurrenceRow> findByVisitOccurrenceId(Integer visitOccurrenceId);
}
