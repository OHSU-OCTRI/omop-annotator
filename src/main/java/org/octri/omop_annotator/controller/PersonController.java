package org.octri.omop_annotator.controller;

import java.util.Map;

import org.octri.omop_annotator.repository.omop.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// TODO: This controller is for demo purposes only.
@Controller
@RequestMapping("/data/person")
public class PersonController {

	@Autowired
	PersonRepository personRepository;

	@GetMapping("/{id}")
	public String show(Map<String, Object> model, @PathVariable Long id) {
		model.put("entity", personRepository.findById(id).get());
		return "person/show";
	}	
}
