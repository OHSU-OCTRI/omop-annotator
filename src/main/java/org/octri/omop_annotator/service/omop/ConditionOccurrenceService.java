package org.octri.omop_annotator.service.omop;

import java.util.List;

import org.octri.omop_annotator.repository.omop.ConditionOccurrenceRepository;
import org.octri.omop_annotator.view.ConditionOccurrenceRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConditionOccurrenceService {

	@Autowired
	private ConditionOccurrenceRepository repository;

	public List<ConditionOccurrenceRow> findByPersonId(Integer personId) {
		return repository.findByPersonId(personId);
	}

	public List<ConditionOccurrenceRow> findByVisitOccurrenceId(Integer visitOccurrenceId) {
		return repository.findByVisitOccurrenceId(visitOccurrenceId);
	}

}
