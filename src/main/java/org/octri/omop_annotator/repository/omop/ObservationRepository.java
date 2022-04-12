package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.Observation;
import org.octri.omop_annotator.view.ObservationRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "observation")
public interface ObservationRepository extends PagingAndSortingRepository<Observation, Long> {

	static final String query = "select obs.id as id, obs.person.id as person, obsConcept.name as name,"
			+ " obsTypeConcept.name as type, obs.observationDatetime as date, obs.valueAsString as value,"
			+ " visitOccurrence.id as visitOccurrence"
			+ " from Observation obs"
			+ " left join obs.observation obsConcept"
			+ " left join obs.observationType obsTypeConcept"
			+ " left join obs.visitOccurrence visitOccurrence";

	@Query(query + " where obs.person.id = ?1")
	List<ObservationRow> findByPersonId(Long id);

	@Query(query + " where visitOccurrence.id = ?1")
	List<ObservationRow> findByVisitOccurrenceId(Long visitOccurrenceId);

}
