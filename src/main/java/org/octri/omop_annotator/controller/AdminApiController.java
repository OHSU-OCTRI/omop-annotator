package org.octri.omop_annotator.controller;

import java.util.List;

import org.octri.omop_annotator.domain.app.TopicSet;
import org.octri.omop_annotator.repository.app.CustomViewRepository;
import org.octri.omop_annotator.repository.app.TopicSetRepository;
import org.octri.omop_annotator.view.TopicSetSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for API endpoints available to admins
 */
@RestController
@RequestMapping("/admin/api")
public class AdminApiController {

    @Autowired
    TopicSetRepository topicSetRepository;

    @Autowired
    CustomViewRepository customViewRepository;

    /**
     * Return all topic sets
     * 
     * @return
     */
    @GetMapping(value = "/topic_sets")
    @ResponseBody
    public Iterable<TopicSet> topicSets() {
        return topicSetRepository.findAll();
    }

    /**
     * Get a summary of all the pools and topics in the topic set and counts of user judgments
     * 
     * @param id
     *            the topic set id
     * @return
     */
    @GetMapping(value = "/topic_set/{id}/summary")
    @ResponseBody
    public List<TopicSetSummary> topicSetSummary(@PathVariable Long id) {
        return customViewRepository.summarizeTopicSet(id);
    }

}
