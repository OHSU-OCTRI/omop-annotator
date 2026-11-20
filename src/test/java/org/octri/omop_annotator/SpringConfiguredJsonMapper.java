package org.octri.omop_annotator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.octri.omop_annotator.config.OmopDataConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * Test annotation that provides the application's {@link tools.jackson.databind.json.JsonMapper} for injection,
 * configured from the spring.jackson.* settings in application.properties. Only Jackson auto-configuration is loaded,
 * so no database or web context is required.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringJUnitConfig({ JacksonAutoConfiguration.class, OmopDataConfiguration.class })
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application.properties")
public @interface SpringConfiguredJsonMapper {

}