package org.octri.omop_annotator.controller;

import java.util.List;

import org.octri.omop_annotator.domain.app.AnnotationLabel;
import org.octri.omop_annotator.repository.app.AnnotationLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for {@link AnnotationLabel} objects.
 */
@RestController
@RequestMapping("/admin/annotation_label")
public class AnnotationLabelController {

	@Autowired
	private AnnotationLabelRepository repository;
	
	@GetMapping(value = "/schema/{schemaId}", produces = "application/json")
	public List<AnnotationLabel> labelsForSchema(@PathVariable Long schemaId) {
		return repository.findByAnnotationSchemaId(schemaId);
	}

}
