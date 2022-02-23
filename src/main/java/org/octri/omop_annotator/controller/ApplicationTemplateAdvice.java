package org.octri.omop_annotator.controller;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Additional advice beyond what is offered in the authentication library
 */
@Component("application_template_advice")
@ControllerAdvice
public class ApplicationTemplateAdvice {

	/**
	 * Redirect any {@link EntityNotFoundException} to the controller method that handles it
	 *
	 * @param request
	 * @param response
	 * @param runtimeException
	 * @throws Exception
	 */
	@ExceptionHandler(value = EntityNotFoundException.class)
	public void defaultErrorHandler(HttpServletRequest request, HttpServletResponse response,
			EntityNotFoundException e) throws IOException, ServletException {
		response.sendRedirect(request.getContextPath() + "/notFound");
	}
}
