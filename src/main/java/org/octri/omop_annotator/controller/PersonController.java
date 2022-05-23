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
import org.octri.omop_annotator.repository.omop.ConceptRepository;
import org.octri.omop_annotator.repository.omop.ConditionOccurrenceRepository;
import org.octri.omop_annotator.repository.omop.DrugExposureRepository;
import org.octri.omop_annotator.repository.omop.MeasurementRepository;
import org.octri.omop_annotator.repository.omop.NoteRepository;
import org.octri.omop_annotator.repository.omop.ObservationRepository;
import org.octri.omop_annotator.repository.omop.PersonRepository;
import org.octri.omop_annotator.repository.omop.ProcedureOccurrenceRepository;
import org.octri.omop_annotator.repository.omop.VisitOccurrenceRepository;
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
	private PersonRepository personRepository;
	private VisitOccurrenceRepository visitOccurrenceRepository;
	private ConditionOccurrenceRepository conditionOccurrenceRepository;
	private ObservationRepository observationRepository;
	private ProcedureOccurrenceRepository procedureOccurrenceRepository;
	private MeasurementRepository measurementRepository;
	private NoteRepository noteRepository;
	private DrugExposureRepository drugExposureRepository;

	@Autowired
	public PersonController(OmopDataConfiguration omopDataConfig, PersonRepository personRepository,
			VisitOccurrenceRepository visitOccurrenceRepository, ConceptRepository conceptRepository,
			ConditionOccurrenceRepository conditionOccurrenceRepository, DrugExposureRepository drugExposureRepository,
			MeasurementRepository measurementRepository, ObservationRepository observationRepository,
			ProcedureOccurrenceRepository procedureOccurrenceRepository, NoteRepository noteRepository) {
		super();
		this.omopDataConfig = omopDataConfig;
		this.personRepository = personRepository;
		this.visitOccurrenceRepository = visitOccurrenceRepository;
		this.conditionOccurrenceRepository = conditionOccurrenceRepository;
		this.drugExposureRepository = drugExposureRepository;
		this.measurementRepository = measurementRepository;
		this.observationRepository = observationRepository;
		this.procedureOccurrenceRepository = procedureOccurrenceRepository;
		this.noteRepository = noteRepository;
		mapper.setDateFormat(new SimpleDateFormat(omopDataConfig.getDateFormat()));
	}

	@GetMapping("/{id}")
	public String show(Map<String, Object> model, @PathVariable Integer id) {
		model.put("pageScripts", new String[] { "vendor.js", "person.js" });
		model.put("entity", personRepository.findById(id).get());

		return "person/show";
	}

	@GetMapping(value = "/summary/{personId}", produces = "application/json")
	@ResponseBody
	public String getPerson(@PathVariable Integer personId) throws JsonProcessingException {
		Person person = personRepository.findById(personId).get();
		person.setAgeCalculationDate(omopDataConfig.getRefreshDate());
		return mapper.writeValueAsString(person);
	}

	@GetMapping(value = "/summary/{personId}/visits", produces = "application/json")
	@ResponseBody
	public String getVisits(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(visitOccurrenceRepository.findByPersonId(personId));
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
			visitIds = visitOccurrenceRepository.findByPersonIdAndProcedureNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.condition) {
			visitIds = visitOccurrenceRepository.findByPersonIdAndConditionNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.observation) {
			visitIds = visitOccurrenceRepository.findByPersonIdAndObservationNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.measurement) {
			visitIds = visitOccurrenceRepository.findByPersonIdAndMeasurementNameLike(personId, searchTerm);
		} else if (entity == FilterEntity.medication) {
			visitIds = visitOccurrenceRepository.findByPersonIdAndDrugNameLike(personId, searchTerm);
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
		var json = mapper.writeValueAsString(conditionOccurrenceRepository.findByPersonId(personId));
		return json;
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/conditions", produces = "application/json")
	@ResponseBody
	public String getVisitConditions(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		var json = mapper.writeValueAsString(conditionOccurrenceRepository.findByVisitOccurrenceId(visitId));
		return json;
	}

	@GetMapping(value = "/summary/{personId}/observations", produces = "application/json")
	@ResponseBody
	public String getObservations(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(observationRepository.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/observations", produces = "application/json")
	@ResponseBody
	public String getVisitObservations(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(observationRepository.findByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/procedures", produces = "application/json")
	@ResponseBody
	public String getProcedures(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(procedureOccurrenceRepository.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/procedures", produces = "application/json")
	@ResponseBody
	public String getVisitProcedures(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(procedureOccurrenceRepository.findByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/measurements", produces = "application/json")
	@ResponseBody
	public String getMeasurements(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(measurementRepository.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/measurements", produces = "application/json")
	@ResponseBody
	public String getVisitMeasurements(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(measurementRepository.findByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/notes", produces = "application/json")
	@ResponseBody
	public String getNotes(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(noteRepository.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/notes", produces = "application/json")
	@ResponseBody
	public String getVisitNotes(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(noteRepository.findByVisitOccurrenceId(visitId));
	}

	@GetMapping(value = "/summary/{personId}/drugs", produces = "application/json")
	@ResponseBody
	public String getDrugs(@PathVariable Integer personId) throws JsonProcessingException {
		return mapper.writeValueAsString(drugExposureRepository.findByPersonId(personId));
	}

	@GetMapping(value = "/summary/{personId}/visit/{visitId}/drugs", produces = "application/json")
	@ResponseBody
	public String getVisitDrugs(@PathVariable Integer personId, @PathVariable Integer visitId)
			throws JsonProcessingException {
		return mapper.writeValueAsString(drugExposureRepository.findByVisitOccurrenceId(visitId));
	}

}
