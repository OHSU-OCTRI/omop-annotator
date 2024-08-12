package org.octri.omop_annotator.service.omop;

import java.util.List;
import java.util.stream.Collectors;

import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.octri.omop_annotator.repository.omop.VisitOccurrenceRepository;
import org.octri.omop_annotator.view.VisitOccurrenceRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitOccurrenceService {

	@Autowired
	VisitOccurrenceRepository repository;

	public List<VisitOccurrenceRow> findAllByPersonId(Integer personId) {
		return repository.findAllByPersonId(personId);
	}

	public List<Integer> findAllByPersonIdAndProcedureNameLike(Integer personId, String procedureName) {
		return repository.findAllByPersonIdAndProcedureNameLike(personId, procedureName);
	}

	public List<Integer> findAllByPersonIdAndConditionNameLike(Integer personId, String conditionName) {
		return repository.findAllByPersonIdAndConditionNameLike(personId, conditionName);
	}

	public List<Integer> findAllByPersonIdAndObservationNameLike(Integer personId, String observationName) {
		return repository.findAllByPersonIdAndObservationNameLike(personId, observationName);
	}

	public List<Integer> findAllByPersonIdAndMeasurementNameLike(Integer personId, String measurementName) {
		return repository.findAllByPersonIdAndMeasurementNameLike(personId, measurementName);
	}

	/**
	 * Perform a text search on notes. This uses Hibernate search instead of a direct query to circumvent Hibernate
	 * validation errors on text fields not explicitly defined as Clob in Oracle. https://jirabp.ohsu.edu/browse/OA-187
	 * 
	 * @param personId
	 * @param noteText
	 * @return
	 */
	public List<Integer> findAllByPersonIdAndNoteContains(Integer personId, String noteText) {
		return repository.searchNotes(personId, noteText).stream().map(VisitOccurrence::getId).sorted()
				.collect(Collectors.toList());
	}

	public List<Integer> findAllByPersonIdAndDrugNameLike(Integer personId, String drugName) {
		return repository.findAllByPersonIdAndDrugNameLike(personId, drugName);
	}

	/**
	 * Do a full text search on the entity and any related entities.
	 *
	 * @param personId
	 * @param text
	 * @return
	 */
	public List<Integer> findAllByPersonIdAndAnyEntityContains(Integer personId, String text) {
		return repository.search(personId, text).stream().map(VisitOccurrence::getId).sorted()
				.collect(Collectors.toList());
	}

	/**
	 * Returns the ids of the visit occurrences for a given person that have associated data.
	 *
	 * @param personId
	 * @return
	 */
	public List<Integer> findAllWithData(Integer personId) {
		return repository.findAllWithData(personId).stream().map(VisitOccurrence::getId).sorted()
				.collect(Collectors.toList());
	}
}
