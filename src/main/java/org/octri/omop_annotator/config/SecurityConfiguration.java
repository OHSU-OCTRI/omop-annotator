package org.octri.omop_annotator.config;

import org.octri.authentication.FormSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
	
	/**
	 * Override security configuration to prevent basic users from accessing admin functions
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		formAuthSuccessHandler.setDefaultTargetUrl(defaultSuccessUrl());
		formAuthFailureHandler.setDefaultFailureUrl(loginFailureRedirectUrl());
		http.exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
				.and()
				.csrf()
				.and()
				.formLogin()
				.permitAll()
				.successHandler(formAuthSuccessHandler)
				.failureHandler(formAuthFailureHandler)
				.and()
				.logout()
				.permitAll()
				.logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl()))
				.logoutSuccessUrl(logoutSuccessUrl())
				.and()
				.authorizeRequests()
				.antMatchers(publicRoutes())
				.permitAll()
				.antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER')") // Basic users can't do admin functions
				.antMatchers(HttpMethod.POST).authenticated()
				.antMatchers(HttpMethod.PUT).authenticated()
				.antMatchers(HttpMethod.PATCH).authenticated()
				.antMatchers(HttpMethod.DELETE).denyAll()
				.anyRequest()
				.authenticated();
	}


}
