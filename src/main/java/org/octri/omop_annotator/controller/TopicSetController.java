package org.octri.omop_annotator.controller;

import org.octri.omop_annotator.domain.app.TopicSet;
import org.octri.omop_annotator.repository.app.TopicSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for {@link TopicSet} objects.
 */
@Controller
@RequestMapping("/data/topic_set")
public class TopicSetController extends AbstractEntityController<TopicSet, TopicSetRepository> {

	@Autowired
	private TopicSetRepository repository;

	@Override
	protected Class<TopicSet> domainClass() {
		return TopicSet.class;
	}

	@Override
	protected TopicSetRepository getRepository() {
		return this.repository;
	}
}
