package org.octri.omop_annotator.controller.rest;

import java.util.List;

import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.octri.omop_annotator.repository.omop.PersonRepository;
import org.octri.omop_annotator.repository.omop.VisitOccurrenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/person")
public class PersonRestController {

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	VisitOccurrenceRepository visitOccurrenceRepository;
	
	@GetMapping(value = "/{personId}/visits", produces = "application/json")
	public List<VisitOccurrence> getVisits(@PathVariable Long personId) {
		return visitOccurrenceRepository.findByPersonId(personId);
	}

}
