package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.Measurement;
import org.octri.omop_annotator.view.MeasurementRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "measurement")
public interface MeasurementRepository extends PagingAndSortingRepository<Measurement, Integer> {

	static final String query = "select m.id as id, m.person.id as person, measurementConcept.name as measurement,"
			+ " measurementTypeConcept.name as measurementType, m.measurementDatetime as measurementDatetime,"
			+ " m.valueAsNumber as valueAsNumber, valueAsConcept.name as valueAsConcept, unit.name as unit,"
			+ " visitOccurrence.id as visitOccurrence"
			+ " from Measurement m"
			+ " left join m.measurement measurementConcept"
			+ " left join m.measurementType measurementTypeConcept"
			+ " left join m.valueAsConcept valueAsConcept"
			+ " left join m.unit unit"
			+ " left join m.visitOccurrence visitOccurrence";

	@Query(query + " where m.person.id = ?1")
	List<MeasurementRow> findByPersonId(Integer id);

	@Query(query + " where visitOccurrence.id = ?1")
	List<MeasurementRow> findByVisitOccurrenceId(Integer visitOccurrenceId);
}
