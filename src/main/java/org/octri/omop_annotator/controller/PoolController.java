package org.octri.omop_annotator.controller;

import java.util.Map;

import org.octri.omop_annotator.domain.app.Pool;
import org.octri.omop_annotator.repository.app.AnnotationSchemaRepository;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.octri.omop_annotator.repository.app.TopicSetRepository;
import org.octri.omop_annotator.view.OptionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for {@link Pool} objects.
 */
@Controller
@RequestMapping("/admin/pool")
public class PoolController extends AbstractEntityController<Pool, PoolRepository> {

	@Autowired
	private PoolRepository repository;

	@Autowired
	private AnnotationSchemaRepository annotationSchemaRepository;

	@Autowired
	private TopicSetRepository topicSetRepository;

	@Autowired
	private PoolEntryRepository poolEntryRepository;

	@Override
	public String show(Map<String, Object> model, @PathVariable Long id) {
		String template = super.show(model, id);

		Long numRelatedPoolEntries = poolEntryRepository.countByPoolId(id);
		model.put("noRelatedPoolEntries", numRelatedPoolEntries == 0);
		return template;
	}

	@Override
	public String newEntity(Map<String, Object> model) {
		String template = super.newEntity(model);

		// Add options for select.
		model.put("topicSetOptions", OptionList.fromSearch(topicSetRepository.findAll(), null));
		model.put("annotationSchemaOptions", OptionList.fromSearch(annotationSchemaRepository.findAll(), null));
		return template;
	}

	@Override
	public String edit(Map<String, Object> model, @PathVariable Long id) {
		String template = super.edit(model, id);

		Pool entity = (Pool) model.get("entity");

		// Add options for select.
		model.put("topicSetOptions", OptionList.fromSearch(topicSetRepository.findAll(), entity.getTopicSet()));
		model.put("annotationSchemaOptions",
				OptionList.fromSearch(annotationSchemaRepository.findAll(), entity.getAnnotationSchema()));
		return template;
	}

	@Override
	public String update(Map<String, Object> model, @PathVariable Long id, @ModelAttribute("entity") Pool entity,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		Long numRelatedPoolEntries = poolEntryRepository.countByPoolId(id);
		if (numRelatedPoolEntries != 0) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"This pool has related pool entries and cannot be edited.");
			return "redirect:/admin/pool/" + id;
		}

		return super.update(model, id, entity, bindingResult, redirectAttributes);
	}

	@Override
	protected Class<Pool> domainClass() {
		return Pool.class;
	}

	@Override
	protected PoolRepository getRepository() {
		return this.repository;
	}
}
