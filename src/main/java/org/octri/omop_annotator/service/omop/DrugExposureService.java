package org.octri.omop_annotator.service.omop;

import java.util.List;

import org.octri.omop_annotator.repository.omop.DrugExposureRepository;
import org.octri.omop_annotator.view.DrugExposureRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugExposureService {

	@Autowired
	private DrugExposureRepository repository;

	public List<DrugExposureRow> findAllByPersonId(Integer personId) {
		return repository.findAllByPersonId(personId);
	}

	public List<DrugExposureRow> findAllByVisitOccurrenceId(Integer visitOccurrenceId) {
		return repository.findAllByVisitOccurrenceId(visitOccurrenceId);
	}

}
