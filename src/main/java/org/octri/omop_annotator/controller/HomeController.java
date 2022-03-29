package org.octri.omop_annotator.controller;

import java.util.Map;

import org.octri.omop_annotator.repository.app.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Home page controller.
 */
@RestController
public class HomeController {
	
	@Autowired
	PoolRepository poolRepository;

	@GetMapping("/")
	public ModelAndView welcome(Map<String, Object> model) {

		model.put("page_title", "OMOP Annotator");
		model.put("pools", poolRepository.findAll());
		model.put("pageWebjars", new String[] { "datatables/js/jquery.dataTables.min.js",
		"datatables/js/dataTables.bootstrap5.min.js" });
		model.put("pageScripts", new String[] { "table-sorting.js" });
		
		return new ModelAndView("home", model);
	}

}
