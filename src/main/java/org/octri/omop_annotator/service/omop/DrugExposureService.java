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

	public List<DrugExposureRow> findByPersonId(Integer personId) {
		return repository.findByPersonId(personId);
	}

	public List<DrugExposureRow> findByVisitOccurrenceId(Integer visitOccurrenceId) {
		return repository.findByVisitOccurrenceId(visitOccurrenceId);
	}

}
