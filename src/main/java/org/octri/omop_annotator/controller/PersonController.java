package org.octri.omop_annotator.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller for {@link Person} objects
 */
@Controller
@RequestMapping("/data/api/person")
public class PersonController {

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
		return mapper.writeValueAsString(visitOccurrenceService.findAllByPersonId(personId));
	}

	/**
	 * Ids of visits with associated data.
	 *
	 * @param personId
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping(value = "/summary/{personId}/visits_with_data", produces = "application/json")
	@ResponseBody
	public String getVisitsWithData(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(visitOccurrenceService.findAllWithData(personId));
	}

	enum FilterEntity {
		condition, procedure, observation, measurement, note, medication, any
	}

	@GetMapping(value = "/summary/{personId}/visits/filter/{entity}")
	@ResponseBody
	public String getFilteredVisitIds(@PathVariable Integer personId, @PathVariable FilterEntity entity,
			@RequestParam(required = true) String name)
			throws JsonProcessingException {

		List<Integer> visitIds = new ArrayList<Integer>();
		String searchTerm = likeQueryValue(name);
		if (entity == FilterEntity.procedure) {
			visitIds = visitOccurrenceService.findAllByPersonIdAndProcedureNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.condition) {
			visitIds = visitOccurrenceService.findAllByPersonIdAndConditionNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.observation) {
			visitIds = visitOccurrenceService.findAllByPersonIdAndObservationNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.measurement) {
			visitIds = visitOccurrenceService.findAllByPersonIdAndMeasurementNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.note) {
			visitIds = visitOccurrenceService.findAllByPersonIdAndNoteContains(personId, wildcardQueryValue(name));
		} else if (entity == FilterEntity.medication) {
			visitIds = visitOccurrenceService.findAllByPersonIdAndDrugNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.any) {
			visitIds = visitOccurrenceService.findAllByPersonIdAndAnyEntityContains(personId,
					wildcardQueryValue(searchTerm));
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

	/**
	 * Converts a search term that would be used in a SQL Like Query to a wildcard
	 * used in Hibernate search. The first % is removed if it exists. The last is converted
	 * to a splat.
	 * 
	 * @param searchTerm
	 * @return
	 */
	private String wildcardQueryValue(String searchTerm) {
		Assert.notNull(searchTerm, "Search term is required");
		searchTerm = searchTerm.startsWith("%") ? searchTerm.replaceFirst("%", "") : searchTerm;
		return searchTerm.toLowerCase().replaceAll("%$", "*");
	}

	@GetMapping(value = "/summary/{personId}/conditions", produces = "application/json")
	@ResponseBody
	public String getConditions(@PathVariable Integer personId) throws JsonProcessingException {
		var json = mapper.writeValueAsString(conditionOccurrenceService.findAllByPersonId(personId));
		return json;
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/conditions", produces = "application/json")
	@ResponseBody
	public String getVisitConditions(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		var json = mapper.writeValueAsString(conditionOccurrenceService.findAllByVisitOccurrenceId(visitId));
		return json;
	}

	@GetMapping(value = "/summary/{personId}/observations", produces = "application/json")
	@ResponseBody
	public String getObservations(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(observationService.findAllByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/observations", produces = "application/json")
	@ResponseBody
	public String getVisitObservations(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(observationService.findAllByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/procedures", produces = "application/json")
	@ResponseBody
	public String getProcedures(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(procedureOccurrenceService.findAllByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/procedures", produces = "application/json")
	@ResponseBody
	public String getVisitProcedures(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(procedureOccurrenceService.findAllByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/measurements", produces = "application/json")
	@ResponseBody
	public String getMeasurements(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(measurementService.findAllByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/measurements", produces = "application/json")
	@ResponseBody
	public String getVisitMeasurements(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(measurementService.findAllByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/notes", produces = "application/json")
	@ResponseBody
	public String getNotes(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(noteService.findAllByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/notes", produces = "application/json")
	@ResponseBody
	public String getVisitNotes(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(noteService.findAllByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/drugs", produces = "application/json")
	@ResponseBody
	public String getDrugs(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(drugExposureService.findAllByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/drugs", produces = "application/json")
	@ResponseBody
	public String getVisitDrugs(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(drugExposureService.findAllByVisitOccurrenceId(visitId));
	}

}
