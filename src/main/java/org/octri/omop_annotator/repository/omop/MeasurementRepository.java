package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.Measurement;
import org.octri.omop_annotator.view.MeasurementRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "measurement")
public interface MeasurementRepository extends PagingAndSortingRepository<Measurement, Long> {

	static final String measurementQuery = "select measurement_id as id, person_id as person, measurement_datetime as measurementDatetime, measurement_concept.concept_name as measurement, measurement_type_concept.concept_name as measurementType, value_as_number as valueAsNumber, value_concept.concept_name as valueAsConcept, unit_concept.concept_name as unit, visit_occurrence_id as visitOccurrence"
			+ " from measurement"
			+ " left join concept measurement_concept on measurement_concept.concept_id = measurement.measurement_concept_id"
			+ " left join concept measurement_type_concept on measurement_type_concept.concept_id = measurement.measurement_type_concept_id"
			+ " left join concept value_concept on value_concept.concept_id = measurement.value_as_concept_id"
			+ " left join concept unit_concept on unit_concept.concept_id = measurement.unit_concept_id"
			+ " where measurement.person_id = ?1";
	
	List<Measurement> findByPersonId(Long id);
	List<Measurement> findByPersonIdAndVisitOccurrenceId(Long personId, Long visitOccurrenceId);

	// TODO: OA-95 ; Eliminate native queries for distribution to other organizations.
	@Query(value = measurementQuery, nativeQuery = true)
	List<MeasurementRow> measurementRows(Long personId);

}
