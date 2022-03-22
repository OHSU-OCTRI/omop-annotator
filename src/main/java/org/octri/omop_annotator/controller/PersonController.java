package org.octri.omop_annotator.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.octri.omop_annotator.domain.omop.Person;
import org.octri.omop_annotator.repository.omop.ConceptRepository;
import org.octri.omop_annotator.repository.omop.ConditionOccurrenceRepository;
import org.octri.omop_annotator.repository.omop.DrugExposureRepository;
import org.octri.omop_annotator.repository.omop.MeasurementRepository;
import org.octri.omop_annotator.repository.omop.ObservationRepository;
import org.octri.omop_annotator.repository.omop.PersonRepository;
import org.octri.omop_annotator.repository.omop.ProcedureOccurrenceRepository;
import org.octri.omop_annotator.repository.omop.VisitOccurrenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller for {@link Person} objects
 */
@Controller
@RequestMapping("/data/person")
public class PersonController {
	
	private static final Log log = LogFactory.getLog(PersonController.class);
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	VisitOccurrenceRepository visitOccurrenceRepository;
	
	@Autowired
	ConceptRepository conceptRepository;

	@Autowired
	ConditionOccurrenceRepository conditionOccurrenceRepository;

	@Autowired
	DrugExposureRepository drugExposureRepository;

	@Autowired
	MeasurementRepository measurementRepository;

	@Autowired
	ObservationRepository observationRepository;

	@Autowired
	ProcedureOccurrenceRepository procedureOccurrenceRepository;

	@GetMapping("/{id}")
	public String show(Map<String, Object> model, @PathVariable Long id) {
		// Test each repo
		log.info(conceptRepository.findById(32020L).get());
		log.info(conditionOccurrenceRepository.findById(51331L).get());
		log.info(drugExposureRepository.findById(130747452L).get());
		log.info(measurementRepository.findById(286707884L).get());
		log.info(observationRepository.findById(6335046L).get());
		log.info(procedureOccurrenceRepository.findById(245585L).get());

		model.put("pageScripts", new String[] { "vendor.js", "person-summary.js", "visit-list.js" });
		model.put("entity", personRepository.findById(id).get());
	
		return "person/show";
	}

	@GetMapping(value = "/summary/{personId}", produces = "application/json")
	@ResponseBody
	public String getPerson(@PathVariable Long personId) throws JsonProcessingException {
		return mapper.writeValueAsString(personRepository.findById(personId).get());
	}

	@GetMapping(value = "/summary/{personId}/visits", produces = "application/json")
	@ResponseBody
	public String getVisits(@PathVariable Long personId) throws JsonProcessingException {
		return mapper.writeValueAsString(visitOccurrenceRepository.findByPersonId(personId));
	}

}
