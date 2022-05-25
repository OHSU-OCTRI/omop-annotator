package org.octri.omop_annotator.service.app;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.apache.commons.lang3.StringUtils;
import org.octri.omop_annotator.domain.app.Topic;
import org.octri.omop_annotator.domain.app.TopicSet;
import org.octri.omop_annotator.repository.app.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TopicUploadService {

	@Autowired
	private TopicRepository topicRepository;

	/**
	 * Validate the file and create topics if there are no errors.
	 *
	 * @param multipartFile
	 * @param topicSet
	 * @return
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public List<UploadResult> uploadTopics(MultipartFile multipartFile, TopicSet topicSet)
			throws IOException, CsvValidationException {

		// Get existing topics for the topic set
		List<Topic> existingTopics = topicRepository.findByTopicSetId(topicSet.getId());
		List<Integer> existingTopicNumbers = existingTopics.stream().map(topic -> topic.getTopicNumber())
				.collect(Collectors.toList());
		List<Integer> fileTopicNumbers = new ArrayList<>();

		List<UploadResult> results = new ArrayList<>();
		Boolean noErrors = true;
		CSVReader reader = new CSVReader(new InputStreamReader(multipartFile.getInputStream()));
		reader.skip(1);
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			List<String> errors = new ArrayList<>();
			String topicNumberAsString = nextLine[0];
			String topicNarrative = nextLine[1];
			Optional<Integer> topicNumber = parseInteger(topicNumberAsString);
			if (StringUtils.isAllBlank(topicNumberAsString)) {
				errors.add("Topic number cannot be blank");
			} else if (StringUtils.isAllBlank(topicNarrative)) {
				errors.add("Topic narrative cannot be blank");
			} else if (topicNumber.isEmpty()) {
				errors.add("Topic " + topicNumberAsString + " is not a number");
			} else if (existingTopicNumbers.contains(topicNumber.get())) {
				errors.add("Topic " + topicNumberAsString + " is already in the topic set.");
			} else if (fileTopicNumbers.contains(topicNumber.get())) {
				errors.add("Topic " + topicNumberAsString + " is in the file twice.");
			}
			Topic topic = null;
			if (errors.isEmpty()) {
				fileTopicNumbers.add(topicNumber.get());
				topic = new Topic();
				topic.setTopicSet(topicSet);
				topic.setTopicNumber(topicNumber.get());
				topic.setNarrative(topicNarrative);
			} else {
				noErrors = false;
			}
			results.add(new UploadResult(topicSet, topicNumberAsString, topicNarrative, topic, errors));
		}
		reader.close();

		if (noErrors) {
			saveAll(results);
		}
		return results;
	}

	/**
	 * @param str
	 * @return an optional of the string parsed as an Integer or empty if parsing
	 *         fails
	 */
	private Optional<Integer> parseInteger(String str) {
		try {
			return Optional.of(Integer.parseInt(str));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	@Transactional
	private void saveAll(List<UploadResult> results) {
		topicRepository.saveAll(results.stream().map(r -> r.getTopic()).collect(Collectors.toList()));
	}

	/**
	 * Helper class (POJO) representing the result of an uploaded topic.
	 *
	 */
	public class UploadResult {

		TopicSet topicSet;
		String topicNumber;
		String topicNarrative;
		Topic topic;
		private List<String> errors;

		public UploadResult(TopicSet topicSet, String topicNumber, String topicNarrative, Topic topic,
				List<String> errors) {
			super();
			this.topicSet = topicSet;
			this.topicNumber = topicNumber;
			this.topicNarrative = topicNarrative;
			this.topic = topic;
			this.errors = errors;
		}

		public Topic getTopic() {
			return topic;
		}

		public void setTopic(Topic topic) {
			this.topic = topic;
		}

		public String getTopicNumber() {
			return topicNumber;
		}

		public void setTopicNumber(String topicNumber) {
			this.topicNumber = topicNumber;
		}

		public String getTopicNarrative() {
			return topicNarrative;
		}

		public void setTopicNarrative(String topicNarrative) {
			this.topicNarrative = topicNarrative;
		}

		public List<String> getErrors() {
			return errors;
		}

		public void setErrors(List<String> errors) {
			this.errors = errors;
		}

		public String getErrorDescription() {
			return StringUtils.join(this.getErrors(), ';');
		}

	}

}
