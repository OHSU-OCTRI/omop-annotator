package org.octri.omop_annotator.controller;

import java.util.Map;

import org.octri.omop_annotator.domain.app.AnnotationLabel;
import org.octri.omop_annotator.repository.app.AnnotationLabelRepository;
import org.octri.omop_annotator.repository.app.AnnotationSchemaRepository;
import org.octri.omop_annotator.view.OptionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for {@link AnnotationLabel} objects.
 */
@Controller
@RequestMapping("/data/annotation_label")
public class AnnotationLabelController extends AbstractEntityController<AnnotationLabel, AnnotationLabelRepository> {

	@Autowired
	private AnnotationLabelRepository repository;

	@Autowired
	private AnnotationSchemaRepository annotationSchemaRepository;

	@Override
	public String newEntity(Map<String, Object> model) {
		String template = super.newEntity(model);

		// Add options for select.
		model.put("annotationSchemaOptions",
				OptionList.fromSearch(annotationSchemaRepository.findAll(), null));
		return template;
	}

	@Override
	public String edit(Map<String, Object> model, @PathVariable Long id) {
		String template = super.edit(model, id);

		AnnotationLabel entity = (AnnotationLabel) model.get("entity");

		// Add options for select.
		model.put("annotationSchemaOptions",
				OptionList.fromSearch(annotationSchemaRepository.findAll(), entity.getAnnotationSchema()));
		return template;
	}

	@Override
	protected Class<AnnotationLabel> domainClass() {
		return AnnotationLabel.class;
	}

	@Override
	protected AnnotationLabelRepository getRepository() {
		return this.repository;
	}
}
