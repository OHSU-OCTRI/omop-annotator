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

	public List<ConditionOccurrenceRow> findAllByPersonId(Integer personId) {
		return repository.findAllByPersonId(personId);
	}

	public List<ConditionOccurrenceRow> findAllByVisitOccurrenceId(Integer visitOccurrenceId) {
		return repository.findAllByVisitOccurrenceId(visitOccurrenceId);
	}

}
