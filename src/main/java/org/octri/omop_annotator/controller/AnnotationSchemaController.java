package org.octri.omop_annotator.controller;

import org.octri.omop_annotator.domain.app.AnnotationSchema;
import org.octri.omop_annotator.repository.app.AnnotationSchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for {@link AnnotationSchema} objects.
 */
@Controller
@RequestMapping("/data/annotation_schema")
public class AnnotationSchemaController extends AbstractEntityController<AnnotationSchema, AnnotationSchemaRepository> {

	@Autowired
	private AnnotationSchemaRepository repository;

	@Override
	protected Class<AnnotationSchema> domainClass() {
		return AnnotationSchema.class;
	}

	@Override
	protected AnnotationSchemaRepository getRepository() {
		return this.repository;
	}
}
