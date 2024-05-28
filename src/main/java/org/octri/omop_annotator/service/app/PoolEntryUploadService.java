package org.octri.omop_annotator.service.app;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.octri.omop_annotator.domain.app.AnnotationSchema;
import org.octri.omop_annotator.domain.app.Pool;
import org.octri.omop_annotator.domain.app.PoolEntry;
import org.octri.omop_annotator.domain.app.Topic;
import org.octri.omop_annotator.domain.app.TopicSet;
import org.octri.omop_annotator.domain.omop.Person;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.octri.omop_annotator.repository.app.TopicRepository;
import org.octri.omop_annotator.service.omop.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.transaction.Transactional;

@Service
public class PoolEntryUploadService {

	@Autowired
	private PoolEntryRepository repository;

	@Autowired
	private PoolRepository poolRepository;

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private PersonService personService;

	private static final int NUM_COLUMNS = 2;
	private static final int TOPIC_NUMBER_COLUMN = 0;
	private static final int PERSON_ID_COLUMN = 1;

	/**
	 * Validate the file and create a new pool and associated pool entries if there are no errors.
	 *
	 * @param multipartFile
	 * @param topicSet
	 * @param poolName
	 * @param poolComments
	 * @param annotationSchema
	 * @return
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public List<UploadResult> uploadPoolEntries(MultipartFile multipartFile, TopicSet topicSet, String poolName,
			String poolComments, AnnotationSchema annotationSchema)
			throws IOException, CsvValidationException {

		// Get the valid topics for the topic set
		List<Topic> validTopics = topicRepository.findByTopicSetId(topicSet.getId());
		List<Integer> validTopicNumbers = validTopics.stream().map(topic -> topic.getTopicNumber())
				.collect(Collectors.toList());

		List<UploadResult> results = new ArrayList<>();
		Boolean noErrors = true;
		CSVReader reader = new CSVReader(new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8));
		reader.skip(1);
		Set<Pair<String, String>> topicPersonPairs = new HashSet<>();
		Map<Integer, Integer> topicNumberSortOrder = new LinkedHashMap<>();
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			List<String> errors = new ArrayList<>();
			String topicAsString = nextLine[TOPIC_NUMBER_COLUMN];
			// Skip blank lines. This prevents index out of bounds errors with Windows-style carriage returns.
			if (nextLine.length < NUM_COLUMNS) {
				continue;
			}
			String personAsString = nextLine[PERSON_ID_COLUMN];
			Optional<Integer> topicNumber = parseInteger(topicAsString);
			if (topicNumber.isEmpty()) {
				errors.add("Topic " + topicAsString + " is not a number");
			} else if (!validTopicNumbers.contains(topicNumber.get())) {
				errors.add("Topic " + topicAsString + " is not in the topic set.");
			}
			Optional<Integer> personId = parseInteger(personAsString);
			if (personId.isEmpty()) {
				errors.add("Person " + personAsString + " is not a number");
			} else {
				Optional<Person> person = personService.findById(Integer.valueOf(personId.get()));
				if (person.isEmpty()) {
					errors.add("Person " + personAsString + " is not in the OMOP database.");
				}
			}
			if (topicPersonPairs.contains(Pair.of(topicAsString, personAsString))) {
				errors.add("Duplicate topic-person combination " + topicAsString + "-" + personAsString);
			} else {
				topicPersonPairs.add(Pair.of(topicAsString, personAsString));
			}

			PoolEntry poolEntry = null;
			if (errors.isEmpty()) {
				Integer topicNum = topicNumber.get();
				poolEntry = new PoolEntry();
				poolEntry.setDocumentId(personId.get());
				poolEntry.setTopic(
						validTopics.stream().filter(topic -> topic.getTopicNumber() == topicNum).findFirst().get());
				if (!topicNumberSortOrder.containsKey(topicNum)) {
					topicNumberSortOrder.put(topicNum, 1);
				}
				Integer sortOrderNumber = topicNumberSortOrder.get(topicNum);
				poolEntry.setSortOrder(sortOrderNumber++);
				topicNumberSortOrder.put(topicNum, sortOrderNumber);
			} else {
				noErrors = false;
			}
			results.add(new UploadResult(topicAsString, personAsString, poolEntry, errors));
		}
		reader.close();

		if (noErrors) {
			saveAll(results, topicSet, poolName, poolComments, annotationSchema);
		}
		return results;
	}

	/**
	 * @param str
	 * @return an optional of the string parsed as an Integer or empty if parsing fails
	 */
	private Optional<Integer> parseInteger(String str) {
		try {
			return Optional.of(Integer.parseInt(str));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	@Transactional
	private void saveAll(List<UploadResult> results, TopicSet topicSet, String poolName, String poolComments,
			AnnotationSchema annotationSchema) {
		// First create the pool
		Pool pool = new Pool();
		pool.setTopicSet(topicSet);
		pool.setName(poolName);
		pool.setComments(poolComments);
		pool.setAnnotationSchema(annotationSchema);
		final Pool savedPool = poolRepository.save(pool);

		// Loop through the results, adding the newly created pool to each before saving
		List<PoolEntry> poolEntries = results.stream().map(result -> {
			PoolEntry entry = result.getPoolEntry();
			entry.setPool(savedPool);
			return entry;
		}).collect(Collectors.toList());
		repository.saveAll(poolEntries);
	}

	/**
	 * Helper class (POJO) representing the result of an uploaded pool entry.
	 * Includes the inputs, the entry if created, and any associated validation errors.
	 *
	 */
	public class UploadResult {

		String topicNumber;
		String personId;
		PoolEntry poolEntry;
		private List<String> errors;

		public UploadResult(String topicNumber, String personId, PoolEntry poolEntry, List<String> errors) {
			super();
			this.topicNumber = topicNumber;
			this.personId = personId;
			this.poolEntry = poolEntry;
			this.errors = errors;
		}

		public String getTopicNumber() {
			return topicNumber;
		}

		public void setTopicNumber(String topicNumber) {
			this.topicNumber = topicNumber;
		}

		public String getPersonId() {
			return personId;
		}

		public void setPersonId(String personId) {
			this.personId = personId;
		}

		public PoolEntry getPoolEntry() {
			return poolEntry;
		}

		public void setPoolEntry(PoolEntry poolEntry) {
			this.poolEntry = poolEntry;
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
