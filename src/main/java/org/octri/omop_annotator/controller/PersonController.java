package org.octri.omop_annotator.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.octri.omop_annotator.config.OmopDataConfiguration;
import org.octri.omop_annotator.domain.omop.Person;
import org.octri.omop_annotator.service.omop.ConditionOccurrenceService;
import org.octri.omop_annotator.service.omop.DrugExposureService;
import org.octri.omop_annotator.service.omop.MeasurementService;
import org.octri.omop_annotator.service.omop.NoteService;
import org.octri.omop_annotator.service.omop.ObservationService;
import org.octri.omop_annotator.service.omop.PersonService;
import org.octri.omop_annotator.service.omop.ProcedureOccurrenceService;
import org.octri.omop_annotator.service.omop.VisitOccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private PersonService personService;
	private VisitOccurrenceService visitOccurrenceService;
	private ConditionOccurrenceService conditionOccurrenceService;
	private ObservationService observationService;
	private ProcedureOccurrenceService procedureOccurrenceService;
	private MeasurementService measurementService;
	private NoteService noteService;
	private DrugExposureService drugExposureService;

	@Autowired
	public PersonController(OmopDataConfiguration omopDataConfig, PersonService personService,
			VisitOccurrenceService visitOccurrenceService, ConditionOccurrenceService conditionOccurrenceService,
			DrugExposureService drugExposureService, MeasurementService measurementService,
			ObservationService observationService, ProcedureOccurrenceService procedureOccurrenceService,
			NoteService noteService) {
		super();
		this.omopDataConfig = omopDataConfig;
		this.personService = personService;
		this.visitOccurrenceService = visitOccurrenceService;
		this.conditionOccurrenceService = conditionOccurrenceService;
		this.drugExposureService = drugExposureService;
		this.measurementService = measurementService;
		this.observationService = observationService;
		this.procedureOccurrenceService = procedureOccurrenceService;
		this.noteService = noteService;
		mapper.setDateFormat(new SimpleDateFormat(omopDataConfig.getDateFormat()));
	}

	@GetMapping("/{id}")
	public String show(Map<String, Object> model, @PathVariable Integer id) {
		model.put("pageScripts", new String[] { "vendor.js", "person.js" });
		model.put("entity", personService.findById(id).get());

		return "person/show";
	}

	@GetMapping(value = "/summary/{personId}", produces = "application/json")
	@ResponseBody
	public String getPerson(@PathVariable Integer personId) throws JsonProcessingException {
		Person person = personService.findById(personId).get();
		person.setAgeCalculationDate(omopDataConfig.getRefreshDate());
		return mapper.writeValueAsString(person);
	}

	@GetMapping(value = "/summary/{personId}/visits", produces = "application/json")
	@ResponseBody
	public String getVisits(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(visitOccurrenceService.findByPersonId(personId));
	}

	enum FilterEntity {
		condition, procedure, observation, measurement, note, medication
	}

	@GetMapping(value = "/summary/{personId}/visits/filter/{entity}")
	@ResponseBody
	public String getFilteredVisitIds(@PathVariable Integer personId, @PathVariable FilterEntity entity,
			@RequestParam(required = true) String name)
			throws JsonProcessingException {

		List<Integer> visitIds = new ArrayList<Integer>();
		String searchTerm = likeQueryValue(name);
		if (entity == FilterEntity.procedure) {
			visitIds = visitOccurrenceService.findByPersonIdAndProcedureNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.condition) {
			visitIds = visitOccurrenceService.findByPersonIdAndConditionNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.observation) {
			visitIds = visitOccurrenceService.findByPersonIdAndObservationNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.measurement) {
			visitIds = visitOccurrenceService.findByPersonIdAndMeasurementNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.medication) {
			visitIds = visitOccurrenceService.findByPersonIdAndDrugNameLike(personId, searchTerm);
		}
		return mapper.writeValueAsString(visitIds);
	}

	/**
	 * @param searchTerm
	 * @return value used in a SQL LIKE query.
	 */
	private String likeQueryValue(String searchTerm) {
		Assert.notNull(searchTerm, "Search term is required");
		String prefix = searchTerm.startsWith("%") ? "" : "%";
		String suffix = searchTerm.endsWith("%") ? "" : "%";
		return prefix + searchTerm.toLowerCase() + suffix;
	}

	@GetMapping(value = "/summary/{personId}/conditions", produces = "application/json")
	@ResponseBody
	public String getConditions(@PathVariable Integer personId) throws JsonProcessingException {
		var json = mapper.writeValueAsString(conditionOccurrenceService.findByPersonId(personId));
		return json;
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/conditions", produces = "application/json")
	@ResponseBody
	public String getVisitConditions(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		var json = mapper.writeValueAsString(conditionOccurrenceService.findByVisitOccurrenceId(visitId));
		return json;
	}

	@GetMapping(value = "/summary/{personId}/observations", produces = "application/json")
	@ResponseBody
	public String getObservations(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(observationService.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/observations", produces = "application/json")
	@ResponseBody
	public String getVisitObservations(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(observationService.findByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/procedures", produces = "application/json")
	@ResponseBody
	public String getProcedures(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(procedureOccurrenceService.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/procedures", produces = "application/json")
	@ResponseBody
	public String getVisitProcedures(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(procedureOccurrenceService.findByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/measurements", produces = "application/json")
	@ResponseBody
	public String getMeasurements(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(measurementService.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/measurements", produces = "application/json")
	@ResponseBody
	public String getVisitMeasurements(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(measurementService.findByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/notes", produces = "application/json")
	@ResponseBody
	public String getNotes(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(noteService.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/notes", produces = "application/json")
	@ResponseBody
	public String getVisitNotes(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(noteService.findByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/drugs", produces = "application/json")
	@ResponseBody
	public String getDrugs(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(drugExposureService.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/drugs", produces = "application/json")
	@ResponseBody
	public String getVisitDrugs(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(drugExposureService.findByVisitOccurrenceId(visitId));
	}

}
