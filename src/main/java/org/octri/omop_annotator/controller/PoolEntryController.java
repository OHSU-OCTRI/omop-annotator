package org.octri.omop_annotator.controller;

import java.util.Map;

import org.octri.omop_annotator.domain.app.PoolEntry;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.octri.omop_annotator.repository.app.TopicRepository;
import org.octri.omop_annotator.view.OptionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for {@link PoolEntry} objects.
 */
@Controller
@RequestMapping("/admin/pool_entry")
public class PoolEntryController extends AbstractEntityController<PoolEntry, PoolEntryRepository> {

	@Autowired
	private PoolEntryRepository repository;

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private PoolRepository poolRepository;

	@Override
	public String newEntity(Map<String, Object> model) {
		String template = super.newEntity(model);

		// Add options for select.
		model.put("poolOptions",
				OptionList.fromSearch(poolRepository.findAll(), null));
		model.put("topicOptions",
				OptionList.fromSearch(topicRepository.findAll(), null));
		return template;
	}

	@Override
	public String edit(Map<String, Object> model, @PathVariable Long id) {
		String template = super.edit(model, id);

		PoolEntry entity = (PoolEntry) model.get("entity");

		// Add options for select.
		model.put("poolOptions",
				OptionList.fromSearch(poolRepository.findAll(), entity.getPool()));
		model.put("topicOptions",
				OptionList.fromSearch(topicRepository.findAll(), entity.getTopic()));
		return template;
	}

	@Override
	protected Class<PoolEntry> domainClass() {
		return PoolEntry.class;
	}

	@Override
	protected PoolEntryRepository getRepository() {
		return this.repository;
	}
}
