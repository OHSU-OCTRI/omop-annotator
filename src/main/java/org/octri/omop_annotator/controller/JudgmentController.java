package org.octri.omop_annotator.controller;

import java.util.Map;

import org.octri.authentication.server.security.repository.UserRepository;
import org.octri.omop_annotator.domain.app.Judgment;
import org.octri.omop_annotator.repository.app.AnnotationLabelRepository;
import org.octri.omop_annotator.repository.app.JudgmentRepository;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.view.OptionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@Override
	protected Class<Judgment> domainClass() {
		return Judgment.class;
	}

	@Override
	protected JudgmentRepository getRepository() {
		return this.repository;
	}
}
