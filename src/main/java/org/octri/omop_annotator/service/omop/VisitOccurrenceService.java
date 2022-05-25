package org.octri.omop_annotator.service.omop;

import java.util.List;

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

	public List<Integer> findAllByPersonIdAndNoteTextLike(Integer personId, String noteText) {
		return repository.findAllByPersonIdAndNoteTextLike(personId, noteText);
	}

	public List<Integer> findAllByPersonIdAndDrugNameLike(Integer personId, String drugName) {
		return repository.findAllByPersonIdAndDrugNameLike(personId, drugName);
	}

}
