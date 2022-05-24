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

	public List<ObservationRow> findByPersonId(Integer id) {
		return repository.findByPersonId(id);
	}

	public List<ObservationRow> findByVisitOccurrenceId(Integer visitOccurrenceId) {
		return repository.findByVisitOccurrenceId(visitOccurrenceId);
	}

}
