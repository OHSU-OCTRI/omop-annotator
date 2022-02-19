package org.octri.omop_annotator.controller;

import org.octri.omop_annotator.domain.Pool;
import org.octri.omop_annotator.repository.PoolRepository;
import org.octri.omop_annotator.repository.AnnotationSchemaRepository;
import org.octri.omop_annotator.repository.TopicSetRepository;
import org.octri.omop_annotator.view.OptionList;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for {@link Pool} objects.
 */
@Controller
@RequestMapping("/data/pool")
public class PoolController extends AbstractEntityController<Pool, PoolRepository> {

	@Autowired
	private PoolRepository repository;

	@Autowired
	private AnnotationSchemaRepository annotationSchemaRepository;

	@Autowired
	private TopicSetRepository topicSetRepository;

	@Override
	public String newEntity(Map<String, Object> model) {
		String template = super.newEntity(model);

		// Add options for select.
		model.put("topicSetOptions",
				OptionList.fromSearch(topicSetRepository.findAll(), null));
		model.put("annotationSchemaOptions",
				OptionList.fromSearch(annotationSchemaRepository.findAll(), null));
		return template;
	}

	@Override
	public String edit(Map<String, Object> model, @PathVariable Long id) {
		String template = super.edit(model, id);

		Pool entity = (Pool) model.get("entity");

		// Add options for select.
		model.put("topicSetOptions",
				OptionList.fromSearch(topicSetRepository.findAll(), entity.getTopicSet()));
		model.put("annotationSchemaOptions",
				OptionList.fromSearch(annotationSchemaRepository.findAll(), entity.getAnnotationSchema()));
		return template;
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