package org.octri.omop_annotator.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.octri.omop_annotator.domain.app.AnnotationLabel;
import org.octri.omop_annotator.domain.app.AnnotationSchema;
import org.octri.omop_annotator.repository.app.AnnotationLabelRepository;
import org.octri.omop_annotator.repository.app.AnnotationSchemaRepository;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for {@link AnnotationSchema} objects.
 */
@Controller
@RequestMapping("/admin/annotation_schema")
public class AnnotationSchemaController extends AbstractEntityController<AnnotationSchema, AnnotationSchemaRepository> {

	@Autowired
	private AnnotationSchemaRepository repository;

	@Autowired
	private AnnotationLabelRepository annotationLabelRepository;

	@Autowired
	private PoolRepository poolRepository;
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Form model class used by the new and edit forms.
	 */
	private class AnnotationSchemaForm {

		private AnnotationSchema schema;
		private List<AnnotationLabel> annotationLabels;

		public AnnotationSchema getSchema() {
			return schema;
		}

		public List<AnnotationLabel> getAnnotationLabels() {
			return annotationLabels;
		}

		public void setAnnotationLabels(List<AnnotationLabel> annotationLabels) {
			this.annotationLabels = annotationLabels;
		}

		public void setSchema(AnnotationSchema schema) {
			this.schema = schema;
		}

		@Override
		public String toString() {
			return "AnnotationSchemaForm [schema=" + schema + ", annotationLabels=" + annotationLabels + "]";
		}

	}

	@Override
	protected Class<AnnotationSchema> domainClass() {
		return AnnotationSchema.class;
	}

	@Override
	protected AnnotationSchemaRepository getRepository() {
		return this.repository;
	}

	@GetMapping("/{id}")
	@Override
	public String show(Map<String, Object> model, @PathVariable Long id) {
		addTemplateAttributes(model);
		model.put("pageScripts", new String[] { "vendor.js", "judge-control.js" });

		AnnotationSchema annotationSchema = repository.findById(id).get();
		var labels = getLabelsForSchema(id);
		model.put("entity", annotationSchema);
		model.put("labels", labels);
		Long numRelatedPools = poolRepository.countByAnnotationSchemaId(id);
		model.put("noRelatedPools", numRelatedPools == 0);

		String labelsJson = "[]";
		try {
			labelsJson = mapper.writeValueAsString(labels);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		model.put("labelsJson", labelsJson);
		return template("show");
	}

	@Override
	public String newEntity(Map<String, Object> model) {
		addTemplateAttributes(model);

		model.put("pageScripts", new String[] { "vendor.js", "annotation-admin.js" });
		AnnotationSchema annotationSchema = newEntity();
		model.put("schema", annotationSchema);

		return template("new");
	}

	@Override
	public String edit(Map<String, Object> model, @PathVariable Long id) {
		addTemplateAttributes(model);

		model.put("pageScripts", new String[] { "vendor.js", "annotation-admin.js" });
		AnnotationSchema annotationSchema = repository.findById(id).get();
		model.put("schema", annotationSchema);

		return template("edit");
	}

	@GetMapping("/{id}/delete")
	@Override
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		// TODO: Once there are judgments we won't be able to delete schemas/labels related so we may need more refined
		// behavior
		AnnotationSchema annotationSchema = repository.findById(id).get();
		Long numRelatedPools = poolRepository.countByAnnotationSchemaId(id);
		if (numRelatedPools != 0) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"This schema has related pools and cannot be deleted.");
			return "redirect:/admin/annotation_schema/" + id;
		}
		annotationLabelRepository.deleteByAnnotationSchema(annotationSchema);
		return super.delete(id, redirectAttributes);
	}

	@PostMapping("/new_combined")
	public String createCombined(RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("annotation_schema_form") AnnotationSchemaForm annotationSchemaForm,
			BindingResult annotationSchemaBinding) {

		AnnotationSchema savedSchema = repository.save(annotationSchemaForm.getSchema());

		List<AnnotationLabel> labels = annotationSchemaForm.getAnnotationLabels();
		if (labels != null && labels.size() > 0) {
			saveAnnotationLabels(savedSchema, labels);
		}

		redirectAttributes.addFlashAttribute("successMessage", this.entityName() + " successfully created.");
		return showRedirect(savedSchema.getId());
	}

	@PostMapping("/edit_combined/{id}")
	public String updateCombined(@PathVariable Long id, RedirectAttributes redirectAttributes,
			@Valid @ModelAttribute("annotation_schema_form") AnnotationSchemaForm annotationSchemaForm,
			BindingResult annotationSchemaBinding) {

		Long schemaId = annotationSchemaForm.getSchema().getId();
		if (schemaId != null) {
			Long numRelatedPools = poolRepository.countByAnnotationSchemaId(schemaId);
			if (numRelatedPools != 0) {
				redirectAttributes.addFlashAttribute("errorMessage",
						"This schema has related pools and cannot be edited.");
				return "redirect:/admin/annotation_schema/" + schemaId;
			}
		}

		AnnotationSchema schema = repository.save(annotationSchemaForm.getSchema());
		annotationLabelRepository.deleteByAnnotationSchema(schema);

		List<AnnotationLabel> annotationLabels = annotationSchemaForm.getAnnotationLabels();
		if (annotationLabels != null && annotationLabels.size() > 0) {
			saveAnnotationLabels(schema, annotationLabels);
		}

		return showRedirect(id);
	}

	private void saveAnnotationLabels(AnnotationSchema savedSchema, List<AnnotationLabel> labels) {
		// Ensure new labels are associated with the schema
		labels.stream().forEach(label -> label.setAnnotationSchema(savedSchema));

		annotationLabelRepository.saveAll(labels);

	}

	/**
	 * Gets the list of annotation labels associated with the schema.
	 *
	 * @param schemaId
	 * @return
	 */
	private List<AnnotationLabel> getLabelsForSchema(Long schemaId) {
		if (schemaId == null) {
			return null;
		}

		return annotationLabelRepository.findByAnnotationSchemaId(schemaId);
	}

}
