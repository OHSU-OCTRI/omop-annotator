package org.octri.omop_annotator.config;

import static org.springframework.security.config.Customizer.withDefaults;

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
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

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
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(routes.getLoginUrl())))
				.csrf(withDefaults());

		securityConfigurer.configureContentSecurityPolicy(http);
		securityConfigurer.configureFormLoginWithDefaults(http);
		securityConfigurer.configureLogoutWithDefaults(http);
		securityConfigurer.configureSamlWithDefaults(http, authManager);

		http.authorizeHttpRequests(authRequests -> authRequests.requestMatchers(routes.getPublicRoutesWithDefaults())
				.permitAll()
				.requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER")
				.requestMatchers(HttpMethod.POST).authenticated()
				.requestMatchers(HttpMethod.PUT).authenticated()
				.requestMatchers(HttpMethod.PATCH).authenticated()
				.requestMatchers(HttpMethod.DELETE).denyAll()
				.anyRequest()
				.authenticated());

		return http.build();
	}

}
