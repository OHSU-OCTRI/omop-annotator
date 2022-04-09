package org.octri.omop_annotator.controller;

import java.text.SimpleDateFormat;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.octri.omop_annotator.config.OmopDataConfiguration;
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

/**
 * Controller for {@link Person} objects
 */
@Controller
@RequestMapping("/data/person")
public class PersonController {

	private static final Log log = LogFactory.getLog(PersonController.class);
	private ObjectMapper mapper = new ObjectMapper();

	private OmopDataConfiguration omopDataConfig;
	private PersonRepository personRepository;
	private VisitOccurrenceRepository visitOccurrenceRepository;
	private ConditionOccurrenceRepository conditionOccurrenceRepository;
	private ObservationRepository observationRepository;
	private ProcedureOccurrenceRepository procedureOccurrenceRepository;

	@Autowired
	public PersonController(OmopDataConfiguration omopDataConfig, PersonRepository personRepository,
			VisitOccurrenceRepository visitOccurrenceRepository, ConceptRepository conceptRepository,
			ConditionOccurrenceRepository conditionOccurrenceRepository, DrugExposureRepository drugExposureRepository,
			MeasurementRepository measurementRepository, ObservationRepository observationRepository,
			ProcedureOccurrenceRepository procedureOccurrenceRepository) {
		super();
		this.omopDataConfig = omopDataConfig;
		this.personRepository = personRepository;
		this.visitOccurrenceRepository = visitOccurrenceRepository;
		this.conditionOccurrenceRepository = conditionOccurrenceRepository;
		this.observationRepository = observationRepository;
		this.procedureOccurrenceRepository = procedureOccurrenceRepository;
		mapper.setDateFormat(new SimpleDateFormat(omopDataConfig.getDateFormat()));
	}

	@GetMapping("/{id}")
	public String show(Map<String, Object> model, @PathVariable Long id) {
		model.put("pageScripts",
				new String[] { "vendor.js", "person-summary.js", "visit-list.js", "condition-list.js", "observation-list.js", "procedure-list.js"});
		model.put("entity", personRepository.findById(id).get());

		return "person/show";
	}

	@GetMapping(value = "/summary/{personId}", produces = "application/json")
	@ResponseBody
	public String getPerson(@PathVariable Long personId) throws JsonProcessingException {
		Person person = personRepository.findById(personId).get();
		person.setAgeCalculationDate(omopDataConfig.getRefreshDate());
		return mapper.writeValueAsString(person);
	}

	@GetMapping(value = "/summary/{personId}/visits", produces = "application/json")
	@ResponseBody
	public String getVisits(@PathVariable Long personId) throws JsonProcessingException {
		// TODO: The following query also works but is (currently) slow.
		//return mapper.writeValueAsString(visitOccurrenceRepository.findByPersonId(personId));
		return mapper.writeValueAsString(visitOccurrenceRepository.visitOccurrenceRows(personId));
	}

	@GetMapping(value = "/summary/{personId}/conditions", produces = "application/json")
	@ResponseBody
	public String getConditions(@PathVariable Long personId) throws JsonProcessingException {
		var json = mapper.writeValueAsString(conditionOccurrenceRepository.findByPersonId(personId));
		return json;
	}
	
	@GetMapping(value = "/summary/{personId}/observations", produces = "application/json")
	@ResponseBody
	public String getObservations(@PathVariable Long personId) throws JsonProcessingException {
		return mapper.writeValueAsString(observationRepository.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/procedures", produces = "application/json")
	@ResponseBody
	public String getProcedures(@PathVariable Long personId) throws JsonProcessingException {
		return mapper.writeValueAsString(procedureOccurrenceRepository.findByPersonId(personId));
	}

}
