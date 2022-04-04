package org.octri.omop_annotator.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.octri.authentication.server.controller.TemplateAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Additional advice beyond what is offered in the authentication library
 */
@Component("application_template_advice")
@ControllerAdvice
public class ApplicationTemplateAdvice {

	@Autowired
	TemplateAdvice templateAdvice;

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public String handleCustomErrorExceptions(HttpServletRequest request, Model model,
			SQLIntegrityConstraintViolationException e) {
		templateAdvice.addDefaultAttributes(request, model);
		model.addAttribute("status", 422);
		model.addAttribute("error", "Unprocessable Entity");
		model.addAttribute("message", "The request violates a constraint in the database: " + e.getMessage());
		model.addAttribute("timestamp", new Date());
		return "error";
	}

}
