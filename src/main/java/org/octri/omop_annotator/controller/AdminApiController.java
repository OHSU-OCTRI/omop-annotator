package org.octri.omop_annotator.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.octri.omop_annotator.domain.app.AnnotationLabel;
import org.octri.omop_annotator.domain.app.Pool;
import org.octri.omop_annotator.domain.app.TopicSet;
import org.octri.omop_annotator.repository.app.AnnotationLabelRepository;
import org.octri.omop_annotator.repository.app.CustomViewRepository;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.octri.omop_annotator.repository.app.TopicSetRepository;
import org.octri.omop_annotator.view.MergePoolsDTO;
import org.octri.omop_annotator.view.MergePoolsResult;
import org.octri.omop_annotator.view.MergeValidationSummary;
import org.octri.omop_annotator.view.PoolSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for API endpoints available to admins
 */
@RestController
@RequestMapping("/admin/api")
public class AdminApiController {

    private static final Log log = LogFactory.getLog(AdminApiController.class);

    @Autowired
    private AnnotationLabelRepository repository;

    @Autowired
    TopicSetRepository topicSetRepository;

    @Autowired
    PoolRepository poolRepository;

    @Autowired
    PoolEntryRepository poolEntryRepository;

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
     * Return all pools
     * 
     * @return
     */
    @GetMapping(value = "/pools")
    @ResponseBody
    public Iterable<Pool> pools() {
        return poolRepository.findAll();
    }

    /**
     * Get the pools for the given topic set
     * 
     * @param id
     *            the topic set id
     * @return
     */
    @GetMapping(value = "/pools/topic_set/{id}")
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
    @GetMapping(value = "/pools/{id}/summary")
    @ResponseBody
    public List<PoolSummary> poolSummary(@PathVariable Long id) {
        return customViewRepository.summarizePool(id);
    }

    @PostMapping(value = "pools/merge_pools", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public MergePoolsResult mergePools(@RequestBody MergePoolsDTO mergePoolsDTO) {
        MergePoolsResult result = new MergePoolsResult(mergePoolsDTO);
        Optional<Pool> mergePool = poolRepository.findById(mergePoolsDTO.getMergePoolId());
        Optional<Pool> destinationPool = poolRepository.findById(mergePoolsDTO.getDestinationPoolId());

        if (!mergePool.isPresent()) {
            result.addError("The pool to merge does not exist.");
        }
        if (!destinationPool.isPresent()) {
            result.addError("The destination pool does not exist.");
        }
        if (mergePool.isPresent() && destinationPool.isPresent()) {
            if (!mergePool.get().getTopicSet().equals(destinationPool.get().getTopicSet())) {
                result.addError("The two pools have different topics sets.");
            }
            if (!mergePool.get().getAnnotationSchema().equals(destinationPool.get().getAnnotationSchema())) {
                result.addError("The two pools have different annotation schemas.");
            }
            List<MergeValidationSummary> validationResults = poolEntryRepository.validatePoolMerge(
                    mergePoolsDTO.getMergePoolId(),
                    mergePoolsDTO.getDestinationPoolId());
            for (var validationResult : validationResults) {
                // Note that we could potentially allow this as long as there aren't duplicate judgments, but let's keep
                // it simple for now
                result.addError("Both pools contain the Patient " + validationResult.getDocumentId()
                        + " for Topic Number " + validationResult.getTopicNumber());
            }
        }

        // The merge should update pool id on all the pool entries for the merge pool, then delete the merge pool
        if (result.getSuccessful()) {
            try {
                poolEntryRepository.mergePools(mergePoolsDTO.getMergePoolId(), mergePoolsDTO.getDestinationPoolId());
                poolRepository.deleteById(mergePoolsDTO.getMergePoolId());
            } catch (Exception e) {
                result.addError("A database error occurred. Check server logs or contact an admin.");
                log.error(e.getMessage());
            }
        }
        return result;

    }

}
