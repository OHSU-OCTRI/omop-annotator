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

	public List<VisitOccurrenceRow> findByPersonId(Integer personId) {
		return repository.findByPersonId(personId);
	}

	public List<Integer> findByPersonIdAndProcedureNameLike(Integer personId, String procedureName) {
		return repository.findByPersonIdAndProcedureNameLike(personId, procedureName);
	}

	public List<Integer> findByPersonIdAndConditionNameLike(Integer personId, String conditionName) {
		return repository.findByPersonIdAndConditionNameLike(personId, conditionName);
	}

	public List<Integer> findByPersonIdAndObservationNameLike(Integer personId, String observationName) {
		return repository.findByPersonIdAndObservationNameLike(personId, observationName);
	}

	public List<Integer> findByPersonIdAndMeasurementNameLike(Integer personId, String measurementName) {
		return repository.findByPersonIdAndMeasurementNameLike(personId, measurementName);
	}

	public List<Integer> findByPersonIdAndDrugNameLike(Integer personId, String drugName) {
		return repository.findByPersonIdAndDrugNameLike(personId, drugName);
	}

}
