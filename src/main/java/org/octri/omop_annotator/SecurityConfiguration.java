package org.octri.omop_annotator;

import org.octri.authentication.FormSecurityConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Custom security configuration.
 */
@Configuration
public class SecurityConfiguration extends FormSecurityConfiguration {

	@Override
	protected String defaultSuccessUrl() {
		return "/";
	}

	@Override
	protected String logoutSuccessUrl() {
		return "/";
	}

	@Override
	protected String[] customPublicRoutes() {
		return new String[] {};
	}

}
