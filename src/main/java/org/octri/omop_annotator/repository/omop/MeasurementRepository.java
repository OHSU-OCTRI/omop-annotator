package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.Measurement;
import org.octri.omop_annotator.view.MeasurementRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * NOTE: Avoid using Repositories directly. To ensure proper request logging and access control,
 * implement a Service class that wraps the repository and use that to manipulate domain entities
 * instead.
 */
@RepositoryRestResource(path = "measurement")
public interface MeasurementRepository extends PagingAndSortingRepository<Measurement, Integer> {

	static final String query = "select m.id as id, m.person.id as person, measurementConcept.name as measurement,"
			+ " measurementTypeConcept.name as measurementType, m.measurementDatetime as measurementDatetime,"
			+ " measurementOperatorConcept.name as measurementOperator, measurementSourceConcept.name as measurementSource,"
			+ " m.measurementSourceValue as measurementSourceValue, m.valueSourceValue as valueSourceValue,"
			+ " m.valueAsNumber as valueAsNumber, valueAsConcept.name as valueAsConcept, unit.name as unit,"
			+ " m.unitSourceValue as unitSourceValue, m.rangeLow as rangeLow, m.rangeHigh as rangeHigh,"
			+ " visitOccurrence.id as visitOccurrence"
			+ " from Measurement m"
			+ " left join m.measurement measurementConcept"
			+ " left join m.measurementType measurementTypeConcept"
			+ " left join m.measurementSource measurementSourceConcept"
			+ " left join m.measurementOperator measurementOperatorConcept"
			+ " left join m.valueAsConcept valueAsConcept"
			+ " left join m.unit unit"
			+ " left join m.visitOccurrence visitOccurrence";

	@Query(query + " where m.person.id = ?1")
	List<MeasurementRow> findAllByPersonId(Integer id);

	@Query(query + " where visitOccurrence.id = ?1")
	List<MeasurementRow> findAllByVisitOccurrenceId(Integer visitOccurrenceId);
}
