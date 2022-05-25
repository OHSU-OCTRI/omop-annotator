package org.octri.omop_annotator.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import org.octri.omop_annotator.domain.app.AnnotationSchema;
import org.octri.omop_annotator.domain.app.PoolEntry;
import org.octri.omop_annotator.domain.app.TopicSet;
import org.octri.omop_annotator.repository.app.AnnotationSchemaRepository;
import org.octri.omop_annotator.repository.app.JudgmentRepository;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.octri.omop_annotator.repository.app.TopicRepository;
import org.octri.omop_annotator.repository.app.TopicSetRepository;
import org.octri.omop_annotator.service.app.PoolEntryUploadService;
import org.octri.omop_annotator.service.app.PoolEntryUploadService.UploadResult;
import org.octri.omop_annotator.view.OptionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for {@link PoolEntry} objects.
 */
@Controller
@RequestMapping("/admin/pool_entry")
public class PoolEntryController extends AbstractEntityController<PoolEntry, PoolEntryRepository> {

	private final String templateFilename = "pool_entry_upload.csv";
	private final String[] templateColumns = new String[] { "topic_number", "person_id" };

	@Autowired
	private PoolEntryRepository repository;

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private AnnotationSchemaRepository annotationSchemaRepository;

	@Autowired
	private PoolRepository poolRepository;

	@Autowired
	private TopicSetRepository topicSetRepository;

	@Autowired
	private PoolEntryUploadService poolEntryUploadService;

	@Autowired
	private JudgmentRepository judgmentRepository;

	@Override
	public String show(Map<String, Object> model, @PathVariable Long id) {
		String template = super.show(model, id);

		Long numRelatedJudgments = judgmentRepository.countByPoolEntryId(id);
		model.put("noRelatedJudgments", numRelatedJudgments == 0);
		return template;
	}

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
	public String update(Map<String, Object> model, @PathVariable Long id, @ModelAttribute("entity") PoolEntry entity,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		Long numRelatedJudgments = judgmentRepository.countByPoolEntryId(id);
		if (numRelatedJudgments != 0) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"This entry has already been judged and cannot be edited.");
			return "redirect:/admin/pool_entry/" + id;
		}

		return super.update(model, id, entity, bindingResult, redirectAttributes);
	}

	/**
	 * Load the bulk upload form
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/upload")
	public String bulkUpload(HttpServletRequest request, ModelMap model) {
		super.addTemplateAttributes(model);
		model.put("topicSetOptions",
				OptionList.fromSearch(topicSetRepository.findAll(), null));
		model.put("annotationSchemaOptions",
				OptionList.fromSearch(annotationSchemaRepository.findAll(), null));

		return template("bulk_upload");
	}

	/**
	 * Bulk upload template
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/upload_template")
	public void bulkUploadTemplate(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws IOException {

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + templateFilename + "\"");
		CSVWriter writer = new CSVWriter(response.getWriter());
		writer.writeNext(templateColumns);
		writer.close();
	}

	/**
	 * Create a new pool and bulk upload pool entries to it
	 *
	 * @param multiPartFile
	 * @param topicSet
	 * @param poolName
	 * @param poolComments
	 * @param annotationSchema
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	@PostMapping("/upload")
	public String uploadFile(@RequestPart(value = "file") MultipartFile multiPartFile, @RequestParam TopicSet topicSet,
			@RequestParam String poolName, @RequestParam(required = false) String poolComments,
			@RequestParam AnnotationSchema annotationSchema,
			HttpServletRequest request, final ModelMap model) throws IOException, CsvValidationException {

		List<UploadResult> results = poolEntryUploadService.uploadPoolEntries(multiPartFile, topicSet, poolName,
				poolComments, annotationSchema);
		model.put("hasResults", !results.isEmpty());
		if (results.isEmpty()) {
			model.put("error", true);
		}

		for (UploadResult record : results) {
			if (!record.getErrors().isEmpty()) {
				model.put("error", true);
			}
		}

		model.put("newPoolEntries", results);
		return template("uploaded");
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
