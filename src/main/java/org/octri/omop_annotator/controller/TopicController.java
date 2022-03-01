package org.octri.omop_annotator.controller;

import java.util.Map;

import org.octri.omop_annotator.domain.app.Topic;
import org.octri.omop_annotator.repository.app.TopicRepository;
import org.octri.omop_annotator.repository.app.TopicSetRepository;
import org.octri.omop_annotator.view.OptionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for {@link Topic} objects.
 */
@Controller
@RequestMapping("/data/topic")
public class TopicController extends AbstractEntityController<Topic, TopicRepository> {

	@Autowired
	private TopicRepository repository;

	@Autowired
	private TopicSetRepository topicSetRepository;

	@Override
	public String newEntity(Map<String, Object> model) {
		String template = super.newEntity(model);

		// Add options for select.
		model.put("topicSetOptions",
				OptionList.fromSearch(topicSetRepository.findAll(), null));
		return template;
	}

	@Override
	public String edit(Map<String, Object> model, @PathVariable Long id) {
		String template = super.edit(model, id);

		Topic entity = (Topic) model.get("entity");

		// Add options for select.
		model.put("topicSetOptions",
				OptionList.fromSearch(topicSetRepository.findAll(), entity.getTopicSet()));
		return template;
	}

	@Override
	protected Class<Topic> domainClass() {
		return Topic.class;
	}

	@Override
	protected TopicRepository getRepository() {
		return this.repository;
	}
}
