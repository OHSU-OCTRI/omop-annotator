package org.octri.omop_annotator.controller;

import java.util.List;

import org.octri.omop_annotator.domain.app.AnnotationLabel;
import org.octri.omop_annotator.domain.app.Pool;
import org.octri.omop_annotator.domain.app.TopicSet;
import org.octri.omop_annotator.repository.app.AnnotationLabelRepository;
import org.octri.omop_annotator.repository.app.CustomViewRepository;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.octri.omop_annotator.repository.app.TopicSetRepository;
import org.octri.omop_annotator.view.PoolSummary;
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
    private AnnotationLabelRepository repository;

    @Autowired
    TopicSetRepository topicSetRepository;

    @Autowired
    PoolRepository poolRepository;

    @Autowired
    CustomViewRepository customViewRepository;

    /**
     * Get the annotation labels for the given schema
     * 
     * @param schemaId
     * @return
     */
    @GetMapping(value = "annotation_label/schema/{schemaId}", produces = "application/json")
    public List<AnnotationLabel> labelsForSchema(@PathVariable Long schemaId) {
        return repository.findByAnnotationSchemaId(schemaId);
    }

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
     * Get the pools for the given topic set
     * 
     * @param id
     *            the topic set id
     * @return
     */
    @GetMapping(value = "/pool/topic_set/{id}")
    @ResponseBody
    public List<Pool> poolsForTopicSet(@PathVariable Long id) {
        return poolRepository.findByTopicSetId(id);
    }

    /**
     * Get a summary of all the topics in the given pool and counts of user judgments
     * 
     * @param id
     *            the pool id
     * @return
     */
    @GetMapping(value = "/pool/{id}/summary")
    @ResponseBody
    public List<PoolSummary> poolSummary(@PathVariable Long id) {
        return customViewRepository.summarizePool(id);
    }

}
