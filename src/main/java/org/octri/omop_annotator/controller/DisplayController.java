package org.octri.omop_annotator.controller;

import org.octri.omop_annotator.domain.app.OmopDisplayConfiguration;
import org.octri.omop_annotator.repository.app.OmopDisplayConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * The ajax call to get display configuration - this is open to all authenticated users.
 */
@RestController
@RequestMapping("/display")
public class DisplayController {

    @Autowired
    OmopDisplayConfigurationRepository omopDisplayConfigurationRepository;

    @GetMapping(value = "/")
    @ResponseBody
    public Iterable<OmopDisplayConfiguration> getDisplayConfiguration()
            throws JsonProcessingException {
        return omopDisplayConfigurationRepository.findAll();
    }

}
