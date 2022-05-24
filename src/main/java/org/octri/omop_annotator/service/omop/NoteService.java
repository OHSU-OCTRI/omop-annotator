package org.octri.omop_annotator.service.omop;

import java.util.List;

import org.octri.omop_annotator.repository.omop.NoteRepository;
import org.octri.omop_annotator.view.NoteRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

	@Autowired
	private NoteRepository repository;

	public List<NoteRow> findAllByPersonId(Integer id) {
		return repository.findAllByPersonId(id);
	}

	public List<NoteRow> findAllByVisitOccurrenceId(Integer visitOccurrenceId) {
		return repository.findAllByVisitOccurrenceId(visitOccurrenceId);
	}

}
