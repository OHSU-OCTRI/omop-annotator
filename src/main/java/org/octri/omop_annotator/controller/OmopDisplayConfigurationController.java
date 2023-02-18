package org.octri.omop_annotator.controller;

import java.util.Map;
import java.util.Optional;

import org.octri.omop_annotator.domain.app.OmopDisplayConfiguration;
import org.octri.omop_annotator.repository.app.OmopDisplayConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for {@link OmopDisplayConfiguration} objects. This is only available to admins.
 */
@Controller
@RequestMapping("/admin/omop_display_configuration")
public class OmopDisplayConfigurationController
		extends AbstractEntityController<OmopDisplayConfiguration, OmopDisplayConfigurationRepository> {

	@Autowired
	private OmopDisplayConfigurationRepository repository;

	@Override
	protected Class<OmopDisplayConfiguration> domainClass() {
		return OmopDisplayConfiguration.class;
	}

	@Override
	protected OmopDisplayConfigurationRepository getRepository() {
		return this.repository;
	}

	// Creating through the UI is not allowed
	@Override
	public String create(Map<String, Object> model, @ModelAttribute("entity") OmopDisplayConfiguration entity,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("errorMessage",
				"New entries must be added through the database.");
		return listingRedirect();
	}

	// Override to redirect back to list. No show page necessary
	@Override
	public String update(Map<String, Object> model, @PathVariable Long id,
			@ModelAttribute("entity") OmopDisplayConfiguration entity,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		Optional<OmopDisplayConfiguration> oldConfiguration = repository.findById(entity.getId());
		if (oldConfiguration.isPresent() && oldConfiguration.get().getEditable()) {
			this.getRepository().save(entity);
			redirectAttributes.addFlashAttribute("infoMessage",
					entity.getEntityName() + "." + entity.getFieldName() + " updated.");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage",
					entity.getEntityName() + "." + entity.getFieldName() + " is not editable.");
		}
		return listingRedirect();
	}

}