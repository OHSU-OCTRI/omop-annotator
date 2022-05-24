package org.octri.omop_annotator.service.omop;

import java.util.List;

import org.octri.omop_annotator.repository.omop.MeasurementRepository;
import org.octri.omop_annotator.view.MeasurementRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

	@Autowired
	private MeasurementRepository repository;

	public List<MeasurementRow> findAllByPersonId(Integer id) {
		return repository.findAllByPersonId(id);
	}

	public List<MeasurementRow> findAllByVisitOccurrenceId(Integer visitOccurrenceId) {
		return repository.findAllByVisitOccurrenceId(visitOccurrenceId);
	}

}
