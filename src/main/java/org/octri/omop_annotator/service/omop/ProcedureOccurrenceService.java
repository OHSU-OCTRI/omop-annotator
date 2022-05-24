package org.octri.omop_annotator.service.omop;

import java.util.List;

import org.octri.omop_annotator.repository.omop.ProcedureOccurrenceRepository;
import org.octri.omop_annotator.view.ProcedureOccurrenceRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcedureOccurrenceService {

	@Autowired
	ProcedureOccurrenceRepository repository;

	public List<ProcedureOccurrenceRow> findAllByPersonId(Integer personId) {
		return repository.findAllByPersonId(personId);
	}

	public List<ProcedureOccurrenceRow> findAllByVisitOccurrenceId(Integer visitOccurrenceId) {
		return repository.findAllByVisitOccurrenceId(visitOccurrenceId);
	}

}
