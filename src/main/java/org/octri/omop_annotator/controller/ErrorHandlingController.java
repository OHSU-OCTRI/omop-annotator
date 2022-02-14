package org.octri.omop_annotator.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handle expected errors
 */
@Controller
public class ErrorHandlingController {
	
	/**
	 * Fill in the model and forward to the standard error page.
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/notFound")
    public String getNotFound(Model model) {
		model.addAttribute("status", 404);
		model.addAttribute("error", "Not Found");
		model.addAttribute("message", "The page you requested was not found.");
		model.addAttribute("timestamp", new Date());
        return "error";
    }

}
