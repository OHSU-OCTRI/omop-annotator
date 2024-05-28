package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.octri.omop_annotator.view.VisitOccurrenceRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * NOTE: Avoid using Repositories directly. To ensure proper request logging and access control,
 * implement a Service class that wraps the repository and use that to manipulate domain entities
 * instead.
 */
@RepositoryRestResource(path = "visit_occurrence")
public interface VisitOccurrenceRepository
		extends ListCrudRepository<VisitOccurrence, Integer>, CustomVisitOccurrenceRepository {

	static final String visitOccurrenceQuery = "select v.id as id, v.person.id as person, visitType.name as visitType,"
			+ " v.visitStart as visitStart, v.visitEnd as visitEnd, provider.providerName as providerName,"
			+ " careSite.careSiteName as careSiteName, visitSource.name as visitSource, v.visitSourceValue as visitSourceValue,"
			+ " v.admittingSourceValue as admittingSourceValue, v.dischargeToSourceValue as dischargeToSourceValue"
			+ " from VisitOccurrence v"
			+ " left join v.visitType visitType"
			+ " left join v.visitSource visitSource"
			+ " left join v.provider provider"
			+ " left join v.careSite careSite"
			+ " where v.person.id = ?1";

	@Query(value = visitOccurrenceQuery)
	List<VisitOccurrenceRow> findAllByPersonId(Integer id);

	@Query(value = "select distinct po.visitOccurrence.id"
			+ " from ProcedureOccurrence po"
			+ " join po.procedure procedure"
			+ " where po.person.id = ?1"
			+ " and lower(procedure.name) like ?2"
			+ " order by po.visitOccurrence.id asc")
	List<Integer> findAllByPersonIdAndProcedureNameLike(Integer personId, String procedureName);

	@Query(value = "select distinct co.visitOccurrence.id"
			+ " from ConditionOccurrence co"
			+ " join co.condition condition"
			+ " where co.person.id = ?1"
			+ " and lower(condition.name) like ?2"
			+ " order by co.visitOccurrence.id asc")
	List<Integer> findAllByPersonIdAndConditionNameLike(Integer personId, String conditionName);

	@Query(value = "select distinct obs.visitOccurrence.id"
			+ " from Observation obs"
			+ " join obs.observation observation"
			+ " where obs.person.id = ?1"
			+ " and lower(observation.name) like ?2"
			+ " order by obs.visitOccurrence.id asc")
	List<Integer> findAllByPersonIdAndObservationNameLike(Integer personId, String observationName);

	@Query(value = "select distinct m.visitOccurrence.id"
			+ " from Measurement m"
			+ " join m.measurement measurement"
			+ " where m.person.id = ?1"
			+ " and lower(measurement.name) like ?2"
			+ " order by m.visitOccurrence.id asc")
	List<Integer> findAllByPersonIdAndMeasurementNameLike(Integer personId, String measurementName);

	@Query(value = "select distinct note.visitOccurrence.id"
			+ " from Note note"
			+ " where note.person.id = ?1"
			+ " and lower(note.text) like ?2"
			+ " order by note.visitOccurrence.id asc")
	List<Integer> findAllByPersonIdAndNoteTextLike(Integer personId, String noteText);

	@Query(value = "select distinct d.visitOccurrence.id"
			+ " from DrugExposure d"
			+ " join d.drug drug"
			+ " where d.person.id = ?1"
			+ " and lower(drug.name) like ?2"
			+ " order by d.visitOccurrence.id asc")
	List<Integer> findAllByPersonIdAndDrugNameLike(Integer personId, String drugName);

	static final String fullTextQuery = "select distinct v.id"
			+ " from VisitOccurrence v"
			+ " left join v.visitType visitType"
			+ " left join v.provider provider"
			+ " left join v.careSite careSite"
			+ " left join ConditionOccurrence co on co.visitOccurrence.id = v.id"
			+ " left join co.condition condition"
			+ " left join Observation obs on obs.visitOccurrence.id = v.id"
			+ " left join obs.observation observation"
			+ " left join ProcedureOccurrence po on po.visitOccurrence.id = v.id"
			+ " left join po.procedure procedure"
			+ " left join Measurement m on m.visitOccurrence.id = v.id"
			+ " left join m.measurement measurement"
			+ " left join Note note on note.visitOccurrence.id = v.id"
			+ " left join DrugExposure d on d.visitOccurrence.id = v.id"
			+ " left join d.drug drug"
			+ " where v.person.id = ?1 and ("
			+ " lower(provider.providerName) like ?2"
			+ " or lower(careSite.careSiteName) like ?2"
			+ " or lower(v.visitSourceValue) like ?2"
			+ " or lower(condition.name) like ?2"
			+ " or lower(observation.name) like ?2"
			+ " or lower(procedure.name) like ?2"
			+ " or lower(measurement.name) like ?2"
			+ " or lower(note.text) like ?2"
			+ " or lower(drug.name) like ?2"
			+ ")"
			+ "order by v.id asc";

	/**
	 * Finds all visit ids for a given person where all of the visits either contain the given text or have a child
	 * entity with the given text.
	 *
	 * @param personId
	 * @param searchTerm
	 * @return
	 */
	@Query(value = fullTextQuery)
	List<Integer> findAllByPersonIdAndAnyEntityContains(Integer personId, String searchTerm);
}
