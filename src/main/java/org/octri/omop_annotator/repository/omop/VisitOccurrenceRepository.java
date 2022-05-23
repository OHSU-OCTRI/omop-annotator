package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.octri.omop_annotator.view.VisitOccurrenceRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "visit_occurrence")
public interface VisitOccurrenceRepository extends PagingAndSortingRepository<VisitOccurrence, Integer> {

	static final String visitOccurrenceQuery = "select v.id as id, v.person.id as person, visitType.name as visitType,"
			+ " v.visitStart as visitStart, v.visitEnd as visitEnd, provider.providerName as providerName, careSite.careSiteName as careSiteName"
			+ " from VisitOccurrence v"
			+ " left join v.visitType visitType"
			+ " left join v.provider provider"
			+ " left join v.careSite careSite"
			+ " where v.person.id = ?1";

	@Query(value = visitOccurrenceQuery)
	List<VisitOccurrenceRow> findByPersonId(Integer id);

	@Query(value = "select distinct po.visitOccurrence.id"
			+ " from ProcedureOccurrence po"
			+ " join po.procedure procedure"
			+ " where po.person.id = ?1"
			+ " and lower(procedure.name) like ?2"
			+ " order by po.visitOccurrence.id asc")
	List<Integer> findByPersonIdAndProcedureNameLike(Integer personId, String procedureName);

	@Query(value = "select distinct co.visitOccurrence.id"
			+ " from ConditionOccurrence co"
			+ " join co.condition condition"
			+ " where co.person.id = ?1"
			+ " and lower(condition.name) like ?2"
			+ " order by co.visitOccurrence.id asc")
	List<Integer> findByPersonIdAndConditionNameLike(Integer personId, String conditionName);

	@Query(value = "select distinct obs.visitOccurrence.id"
			+ " from Observation obs"
			+ " join obs.observation observation"
			+ " where obs.person.id = ?1"
			+ " and lower(observation.name) like ?2"
			+ " order by obs.visitOccurrence.id asc")
	List<Integer> findByPersonIdAndObservationNameLike(Integer personId, String observationName);

	@Query(value = "select distinct m.visitOccurrence.id"
			+ " from Measurement m"
			+ " join m.measurement measurement"
			+ " where m.person.id = ?1"
			+ " and lower(measurement.name) like ?2"
			+ " order by m.visitOccurrence.id asc")
	List<Integer> findByPersonIdAndMeasurementNameLike(Integer personId, String measurementName);

	@Query(value = "select distinct note.visitOccurrence.id"
			+ " from Note note"
			+ " where note.person.id = ?1"
			+ " and lower(note.text) like ?2"
			+ " order by note.visitOccurrence.id asc")
	List<Integer> findByPersonIdAndNoteTextLike(Integer personId, String noteText);

	@Query(value = "select distinct d.visitOccurrence.id"
			+ " from DrugExposure d"
			+ " join d.drug drug"
			+ " where d.person.id = ?1"
			+ " and lower(drug.name) like ?2"
			+ " order by d.visitOccurrence.id asc")
	List<Integer> findByPersonIdAndDrugNameLike(Integer personId, String drugName);
}
