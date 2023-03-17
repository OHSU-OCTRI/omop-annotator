package org.octri.omop_annotator.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.octri.authentication.server.security.AuthenticationUserDetailsService;
import org.octri.authentication.server.security.service.UserService;
import org.octri.omop_annotator.domain.app.PoolEntry;
import org.octri.omop_annotator.domain.app.SearchIndexJob;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.repository.app.SearchIndexJobRepository;
import org.octri.omop_annotator.search.IndexException;
import org.octri.omop_annotator.search.Indexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for {@link SearchIndexJob} objects.
 */
@Controller
@RequestMapping("/admin/search_indexing")
public class SearchIndexJobController extends AbstractBaseEntityController<SearchIndexJob, SearchIndexJobRepository> {

	@Autowired
	private AuthenticationUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private SearchIndexJobRepository repository;

	@Autowired
	private PoolEntryRepository poolEntryRepository;

	@Autowired
	private Indexer indexer;

	/**
	 * Main landing page.
	 */
	@GetMapping("/")
	@Override
	public String list(Map<String, Object> model) {
		var startedJob = getRepository().findByStatus(SearchIndexJob.JobStatus.STARTED);
		model.put("showStartButton", !startedJob.isPresent());
		return super.list(model);
	}

	/**
	 * Start (re)indexing the OMOP data.
	 *
	 * @throws IndexException
	 */
	@GetMapping("/start")
	public String initIndex(Map<String, Object> model, RedirectAttributes redirectAttributes) throws IndexException {
		// Create the SearchIndexJob
		var startedJob = getRepository().findByStatus(SearchIndexJob.JobStatus.STARTED);
		String msg = "";
		if (startedJob.isPresent()) {
			msg = "Indexing job in progress. Refresh this page to check the status.";
		} else {
			var currentUser = userService.find(userDetailsService.getCurrent().getUserId());
			SearchIndexJob job = getRepository().save(new SearchIndexJob(currentUser));

			// Limit indexing to the entities related to the Person ids used in the application.
			List<Integer> ids = StreamSupport.stream(poolEntryRepository.findAll().spliterator(), false)
					.map(PoolEntry::getDocumentId)
					.distinct()
					.collect(Collectors.toList());
			job.setPatientIdCount(ids.size());
			// Starts the asynchronous process
			indexer.indexPersistedData(job, ids);
			msg = "Indexing initiated. Refresh this page to check the status.";
		}
		redirectAttributes.addFlashAttribute("infoMessage", msg);
		return listingRedirect();
	}

	@Override
	protected Class<SearchIndexJob> domainClass() {
		return SearchIndexJob.class;
	}

	@Override
	protected SearchIndexJobRepository getRepository() {
		return this.repository;
	}
}