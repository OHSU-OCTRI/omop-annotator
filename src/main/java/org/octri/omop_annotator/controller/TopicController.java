package org.octri.omop_annotator.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.octri.omop_annotator.domain.app.Topic;
import org.octri.omop_annotator.domain.app.TopicSet;
import org.octri.omop_annotator.repository.app.TopicRepository;
import org.octri.omop_annotator.repository.app.TopicSetRepository;
import org.octri.omop_annotator.service.app.TopicUploadService;
import org.octri.omop_annotator.service.app.TopicUploadService.UploadResult;
import org.octri.omop_annotator.view.OptionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller for {@link Topic} objects.
 */
@Controller
@RequestMapping("/admin/topic")
public class TopicController extends AbstractEntityController<Topic, TopicRepository> {

	private final String templateFilename = "topic_upload.csv";
	private final String[] templateColumns = new String[] { "topic_number", "narrative" };

	@Autowired
	private TopicRepository repository;

	@Autowired
	private TopicSetRepository topicSetRepository;

	@Autowired
	private TopicUploadService topicUploadService;

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
	 * Performs a bulk upload of Topics to a Topic Set
	 *
	 * @param multiPartFile
	 * @param topicSet
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	@PostMapping("/upload")
	public String uploadFile(@RequestPart(value = "file") MultipartFile multiPartFile, @RequestParam TopicSet topicSet,
			HttpServletRequest request, final ModelMap model) throws IOException, CsvValidationException {

		List<UploadResult> results = topicUploadService.uploadTopics(multiPartFile, topicSet);
		model.put("hasResults", !results.isEmpty());
		if (results.isEmpty()) {
			model.put("error", true);
		}

		for (UploadResult record : results) {
			if (!record.getErrors().isEmpty()) {
				model.put("error", true);
			}
		}

		model.put("newTopics", results);
		return template("uploaded");
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
