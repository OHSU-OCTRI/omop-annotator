package org.octri.omop_annotator.service.omop;

import java.util.List;

import org.octri.omop_annotator.repository.omop.ObservationRepository;
import org.octri.omop_annotator.view.ObservationRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObservationService {

	@Autowired
	private ObservationRepository repository;

	public List<ObservationRow> findAllByPersonId(Integer id) {
		return repository.findAllByPersonId(id);
	}

	public List<ObservationRow> findAllByVisitOccurrenceId(Integer visitOccurrenceId) {
		return repository.findAllByVisitOccurrenceId(visitOccurrenceId);
	}

}
