package org.octri.omop_annotator.controller;

import java.util.List;

import org.octri.omop_annotator.domain.app.OmopDisplayConfiguration;
import org.octri.omop_annotator.repository.app.OmopDisplayConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The ajax call to get display configuration for an entity - this is open to all authenticated users.
 */
@Controller
@RequestMapping("/display")
public class DisplayController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    OmopDisplayConfigurationRepository omopDisplayConfigurationRepository;

    @GetMapping(value = "/{entityName}", produces = "application/json")
    @ResponseBody
    public String getDisplayConfiguration(@PathVariable String entityName) throws JsonProcessingException {
        List<OmopDisplayConfiguration> configuration = omopDisplayConfigurationRepository
                .findAllByEntityName(entityName);
        return mapper.writeValueAsString(configuration);
    }

}
