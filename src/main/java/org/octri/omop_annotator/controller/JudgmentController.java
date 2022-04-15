package org.octri.omop_annotator.controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.octri.authentication.server.security.SecurityHelper;
import org.octri.authentication.server.security.repository.UserRepository;
import org.octri.omop_annotator.domain.app.AnnotationLabel;
import org.octri.omop_annotator.domain.app.Judgment;
import org.octri.omop_annotator.domain.app.PoolEntry;
import org.octri.omop_annotator.repository.app.AnnotationLabelRepository;
import org.octri.omop_annotator.repository.app.JudgmentRepository;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.view.JudgmentDTO;
import org.octri.omop_annotator.view.OptionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for {@link Judgment} objects.
 */
@Controller
@RequestMapping("/data/judgment")
public class JudgmentController extends AbstractEntityController<Judgment, JudgmentRepository> {

	@Autowired
	private JudgmentRepository repository;

	@Autowired
	private AnnotationLabelRepository annotationLabelRepository;

	@Autowired
	private PoolEntryRepository poolEntryRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public String newEntity(Map<String, Object> model) {
		String template = super.newEntity(model);

		// Add options for select.
		model.put("userOptions",
				org.octri.authentication.server.view.OptionList.fromSearch(userRepository.findAll(), null));
		model.put("poolEntryOptions",
				OptionList.fromSearch(poolEntryRepository.findAll(), null));
		model.put("annotationLabelOptions",
				OptionList.fromSearch(annotationLabelRepository.findAll(), null));
		return template;
	}

	@Override
	public String edit(Map<String, Object> model, @PathVariable Long id) {
		String template = super.edit(model, id);

		Judgment entity = (Judgment) model.get("entity");

		// Add options for select.
		model.put("userOptions",
				org.octri.authentication.server.view.OptionList.fromSearch(userRepository.findAll(), entity.getUser()));
		model.put("poolEntryOptions",
				OptionList.fromSearch(poolEntryRepository.findAll(), entity.getPoolEntry()));
		model.put("annotationLabelOptions",
				OptionList.fromSearch(annotationLabelRepository.findAll(), entity.getAnnotationLabel()));
		return template;
	}

	/**
	 * API endpoint for getting existing {@link Judgment} information for the given {@link PoolEntry}
	 *
	 * @param poolEntryId
	 *            - id of the {@link PoolEntry} to be judged
	 * @return DTO with the relevant Judgment information
	 * @throws JsonProcessingException
	 */
	@GetMapping(value = "/pool_entry/{poolEntryId}", produces = "application/json")
	@ResponseBody
	public JudgmentDTO getJudgmentForPoolEntry(@PathVariable Long poolEntryId) throws JsonProcessingException {
		var securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
		var userId = securityHelper.authenticationUserDetails().getUserId();
		var judgment = getRepository().findByUserIdAndPoolEntryId(userId, poolEntryId);

		var dto = new JudgmentDTO();
		if (judgment != null) {
			dto.initialize(judgment);
		} else {
			dto.setUserId(userId);
			dto.setPoolEntryId(poolEntryId);
		}
		dto.setAnnotationLabels(labelsForPoolEntry(poolEntryId));
		return dto;
	}

	/**
	 * API endpoint for saving and updating a judgment
	 *
	 * @param dto
	 * @return dto with updated judgment information.
	 * @throws JsonProcessingException
	 */
	@PostMapping(value = "/save_judgment", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public JudgmentDTO saveJudgment(@RequestBody JudgmentDTO dto) throws JsonProcessingException {
		var judgment = new Judgment();
		if (dto.getId() != null) {
			judgment = this.getRepository().findById(dto.getId()).get();
		}
		judgment.setUser(userRepository.findById(dto.getUserId()).get());
		judgment.setPoolEntry(poolEntryRepository.findById(dto.getPoolEntryId()).get());
		judgment.setAnnotationLabel(annotationLabelRepository.findById(dto.getAnnotationLabelId()).get());

		var saved = this.getRepository().save(judgment);
		dto.setId(saved.getId());
		return dto;
	}

	/**
	 * Get the {@link AnnotationLabel} options for the given {@link PoolEntry}.
	 *
	 * @param poolEntryId
	 *            - id of the pool entry to annotate
	 * @return list of applicable labels
	 */
	private List<AnnotationLabel> labelsForPoolEntry(Long poolEntryId) {
		var poolEntry = poolEntryRepository.findById(poolEntryId).get();
		return annotationLabelRepository
				.findByAnnotationSchemaId(poolEntry.getPool().getAnnotationSchema().getId());
	}

	@Override
	protected Class<Judgment> domainClass() {
		return Judgment.class;
	}

	@Override
	protected JudgmentRepository getRepository() {
		return this.repository;
	}
}
