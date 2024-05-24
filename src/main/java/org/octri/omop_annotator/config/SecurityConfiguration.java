package org.octri.omop_annotator.config;

import org.octri.authentication.DefaultSecurityConfigurer;
import org.octri.authentication.config.AuthenticationRouteProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Custom security configuration.
 */
@Configuration
public class SecurityConfiguration {

	@Autowired
	private AuthenticationRouteProperties routes;

	@Autowired
	private DefaultSecurityConfigurer securityConfigurer;

	/**
	 * Set up basic authentication and restrict requests based on HTTP methods,
	 * URSL, and roles.
	 */
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		securityConfigurer.configureAuthenticationManager(authBuilder);
		AuthenticationManager authManager = authBuilder.build();

		http.authenticationManager(authManager)
				.exceptionHandling()
				// .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(routes.getLoginUrl()))
				.and()
				.csrf();

		securityConfigurer.configureContentSecurityPolicy(http);
		securityConfigurer.configureFormLoginWithDefaults(http);
		securityConfigurer.configureLogoutWithDefaults(http);
		securityConfigurer.configureSamlWithDefaults(http, authManager);

		http.authorizeRequests()
				.antMatchers(routes.getPublicRoutesWithDefaults())
				.permitAll()
				.antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER')") // Basic users can't do admin
																							 // functions
				.antMatchers(HttpMethod.POST).authenticated()
				.antMatchers(HttpMethod.PUT).authenticated()
				.antMatchers(HttpMethod.PATCH).authenticated()
				.antMatchers(HttpMethod.DELETE).denyAll()
				.anyRequest()
				.authenticated();

		return http.build();
	}

}
