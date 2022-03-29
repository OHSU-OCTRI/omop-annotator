package org.octri.omop_annotator.controller;

import java.util.Map;

import org.octri.authentication.server.security.SecurityHelper;
import org.octri.omop_annotator.repository.app.CustomViewRepository;
import org.octri.omop_annotator.repository.app.JudgmentRepository;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.octri.omop_annotator.repository.app.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/judge")
public class JudgeController {
	
	@Autowired
	PoolRepository poolRepository;

	@Autowired
	CustomViewRepository customViewRepository;
	
	@Autowired
	TopicRepository topicRepository;
	
	@Autowired
	PoolEntryRepository poolEntryRepository;

	@Autowired
	JudgmentRepository judgmentRepository;

	@GetMapping("/pool/{id}")
	public String showTopicsForPool(Map<String, Object> model, @PathVariable Long id) {
		
		SecurityHelper securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
		
		model.put("pool", poolRepository.findById(id).get());
		model.put("topicJudgments", customViewRepository.summarizeTopicJudgments(id, securityHelper.authenticationUserDetails().getUserId()));
		model.put("pageWebjars", new String[] { "datatables/js/jquery.dataTables.min.js",
		"datatables/js/dataTables.bootstrap5.min.js" });
		model.put("pageScripts", new String[] { "table-sorting.js" });
		return "/judge/show_topics";		
	}
	
	@GetMapping("/pool/{poolId}/topic/{topicId}")
	public String showPoolEntriesForTopic(Map<String, Object> model, @PathVariable Long poolId, @PathVariable Long topicId) {
		model.put("pool", poolRepository.findById(poolId).get());
		model.put("topic", topicRepository.findById(topicId).get());
		model.put("pageScripts", new String[] { "vendor.js", "pool-entries.js"});
		return "judge/show_pool_entries";
	}
	
	@GetMapping(value = "/pool/{poolId}/topic/{topicId}/pool_entry_judgments", produces = "application/json")
	@ResponseBody
	public String getPoolEntryJudgments(@PathVariable Long poolId, @PathVariable Long topicId) throws JsonProcessingException {
		SecurityHelper securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(customViewRepository.summarizePoolEntryJudgments(poolId, topicId, securityHelper.authenticationUserDetails().getUserId()));
	}
	
}
