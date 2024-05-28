package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.Observation;
import org.octri.omop_annotator.view.ObservationRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * NOTE: Avoid using Repositories directly. To ensure proper request logging and access control,
 * implement a Service class that wraps the repository and use that to manipulate domain entities
 * instead.
 */
@RepositoryRestResource(path = "observation")
public interface ObservationRepository extends ListCrudRepository<Observation, Integer> {

	static final String query = "select obs.id as id, obs.person.id as person, obsConcept.name as observation,"
			+ " obsSourceConcept.name as observationSource, obs.observationSourceValue as observationSourceValue, "
			+ " obsTypeConcept.name as observationType, obs.observationDatetime as date, obs.valueAsString as valueAsString, "
			+ " obs.valueAsNumber as valueAsNumber, obsValueConcept.name as valueAsConcept, "
			+ " obsQualifierConcept.name as observationQualifier, obs.qualifierSourceValue as qualifierSourceValue,"
			+ " obsUnitConcept.name as observationUnit, obs.unitSourceValue as unitSourceValue,"
			+ " visitOccurrence.id as visitOccurrence"
			+ " from Observation obs"
			+ " left join obs.observation obsConcept"
			+ " left join obs.observationType obsTypeConcept"
			+ " left join obs.observationSource obsSourceConcept"
			+ " left join obs.qualifier obsQualifierConcept"
			+ " left join obs.unit obsUnitConcept"
			+ " left join obs.valueAsConcept obsValueConcept"
			+ " left join obs.visitOccurrence visitOccurrence";

	@Query(query + " where obs.person.id = ?1")
	List<ObservationRow> findAllByPersonId(Integer id);

	@Query(query + " where visitOccurrence.id = ?1")
	List<ObservationRow> findAllByVisitOccurrenceId(Integer visitOccurrenceId);

}
