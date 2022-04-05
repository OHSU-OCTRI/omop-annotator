package org.octri.omop_annotator.controller;

import java.util.Map;

import org.octri.authentication.server.security.SecurityHelper;
import org.octri.omop_annotator.repository.app.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

		SecurityHelper securityHelper = new SecurityHelper(SecurityContextHolder.getContext());
		if (securityHelper.isLoggedIn()) {
			model.put("page_title", "OMOP Annotator");
			model.put("pools", poolRepository.findAll());
			return new ModelAndView("home", model);
		}

		return new ModelAndView("login", model);
	}

}
