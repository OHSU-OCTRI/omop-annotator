package org.octri.omop_annotator.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.octri.authentication.server.security.SecurityHelper;
import org.octri.authentication.server.security.repository.UserRepository;
import org.octri.omop_annotator.domain.app.AnnotationLabel;
import org.octri.omop_annotator.domain.app.Judgment;
import org.octri.omop_annotator.domain.app.OmopDisplayConfiguration;
import org.octri.omop_annotator.domain.app.OmopEntity;
import org.octri.omop_annotator.domain.app.Pin;
import org.octri.omop_annotator.domain.app.PoolEntry;
import org.octri.omop_annotator.repository.app.AnnotationLabelRepository;
import org.octri.omop_annotator.repository.app.CustomViewRepository;
import org.octri.omop_annotator.repository.app.JudgmentRepository;
import org.octri.omop_annotator.repository.app.OmopDisplayConfigurationRepository;
import org.octri.omop_annotator.repository.app.PinRepository;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.view.JudgmentDTO;
import org.octri.omop_annotator.view.PinDTO;
import org.octri.omop_annotator.view.PoolEntryJudgmentSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * The controller for API endpoints available to all authenticated users
 */
@RestController
@RequestMapping("/data/api")
public class DataApiController {

    @Autowired
    private AnnotationLabelRepository annotationLabelRepository;

    @Autowired
    CustomViewRepository customViewRepository;

    @Autowired
    private JudgmentRepository judgmentRepository;

    @Autowired
    OmopDisplayConfigurationRepository omopDisplayConfigurationRepository;

    @Autowired
    private PoolEntryRepository poolEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PinRepository pinRepository;

    /**
     * Get all display configuration for OMOP entities
     * 
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/omop_display_configuration")
    @ResponseBody
    public Iterable<OmopDisplayConfiguration> getDisplayConfiguration()
            throws JsonProcessingException {
        return omopDisplayConfigurationRepository.findAll();
    }

    /**
     * Get a summary of the current user's judgments and pool_entries for the pool and topic
     * 
     * @param poolId
     * @param topicId
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/pool/{poolId}/topic/{topicId}/pool_entry_judgments")
    @ResponseBody
    public List<PoolEntryJudgmentSummary> getPoolEntryJudgments(@PathVariable Long poolId, @PathVariable Long topicId) {
        SecurityHelper securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
        return customViewRepository.summarizePoolEntryJudgments(poolId, topicId,
                securityHelper.authenticationUserDetails().getUserId());
    }

    /**
     * API endpoint for getting existing {@link Judgment} information for the given {@link PoolEntry}
     *
     * @param poolEntryId
     *            - id of the {@link PoolEntry} to be judged
     * @return DTO with the relevant Judgment information
     * @throws JsonProcessingException
     */
    @GetMapping(value = "judgment/pool_entry/{poolEntryId}")
    @ResponseBody
    public JudgmentDTO getJudgmentForPoolEntry(@PathVariable Long poolEntryId) {
        var securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
        var userId = securityHelper.authenticationUserDetails().getUserId();
        var judgment = judgmentRepository.findByUserIdAndPoolEntryId(userId, poolEntryId);

        var dto = new JudgmentDTO();
        if (judgment != null) {
            dto.initialize(judgment);
        } else {
            dto.setUserId(userId);
            dto.setPoolEntryId(poolEntryId);
        }
        dto.setAnnotationLabels(labelsForPoolEntry(poolEntryId));
        return dto;
    }

    /**
     * API endpoint for saving and updating a judgment
     *
     * @param dto
     * @return dto with updated judgment information.
     * @throws JsonProcessingException
     */
    @PostMapping(value = "judgment/save_judgment", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public JudgmentDTO saveJudgment(@RequestBody JudgmentDTO dto) {
        var judgment = new Judgment();
        if (dto.getId() != null) {
            judgment = judgmentRepository.findById(dto.getId()).get();
        }
        judgment.setUser(userRepository.findById(dto.getUserId()).get());
        judgment.setPoolEntry(poolEntryRepository.findById(dto.getPoolEntryId()).get());
        judgment.setAnnotationLabel(annotationLabelRepository.findById(dto.getAnnotationLabelId()).get());

        var saved = judgmentRepository.save(judgment);
        dto.setId(saved.getId());
        return dto;
    }

    /**
     * API endpoint for getting the user's existing {@link Pin} information for the given {@link PoolEntry}
     *
     * @param poolEntryId
     *            - id of the {@link PoolEntry} to be judged
     * @return the list of Pins for the PoolEntry and User
     * @throws JsonProcessingException
     */
    @GetMapping(value = "pin/pool_entry/{poolEntryId}")
    @ResponseBody
    public List<PinDTO> getPinsForPoolEntry(@PathVariable Long poolEntryId) {
        var securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
        var userId = securityHelper.authenticationUserDetails().getUserId();
        var pins = pinRepository.findByUserIdAndPoolEntryId(userId, poolEntryId);
        return pins.stream().map(pin -> {
            var dto = new PinDTO();
            dto.initialize(pin);
            return dto;
        }).collect(Collectors.toList());
    }

    @PostMapping(value = "pin/save_pin", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public PinDTO savePin(@RequestBody PinDTO dto) {
        var securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
        var userId = securityHelper.authenticationUserDetails().getUserId();
        var pin = new Pin();
        pin.setUser(userRepository.findById(userId).get());
        pin.setPoolEntry(poolEntryRepository.findById(dto.getPoolEntryId()).get());
        pin.setEntity(OmopEntity.valueOf(dto.getEntity()));
        pin.setEntityId(dto.getEntityId());
        pin.setVisitId(dto.getVisitId());
        var saved = pinRepository.save(pin);
        dto.setId(pin.getId());
        dto.setUserId(userId);
        return dto;
    }

    @PostMapping(value = "pin/delete_pin", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public PinDTO deletePin(@RequestBody PinDTO dto) {
        Optional<Pin> pin = pinRepository.findById(dto.getId());
        if (pin.isPresent()) {
            pinRepository.delete(pin.get());
        }
        return dto;
    }

    /**
     * Get the {@link AnnotationLabel} options for the given {@link PoolEntry}.
     *
     * @param poolEntryId
     *            - id of the pool entry to annotate
     * @return list of applicable labels
     */
    private List<AnnotationLabel> labelsForPoolEntry(Long poolEntryId) {
        var poolEntry = poolEntryRepository.findById(poolEntryId).get();
        return annotationLabelRepository
                .findByAnnotationSchemaId(poolEntry.getPool().getAnnotationSchema().getId());
    }

}
