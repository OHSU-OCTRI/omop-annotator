package org.octri.omop_annotator.domain.omop;

import org.octri.omop_annotator.hibernate.annotation.PostgresFloat;
import org.springframework.context.annotation.Configuration;

/**
 * This class determines whether the PostgresFloat conditional annotation is applied. The PostgresFloat annotation
 * cannot be used directly by domain entities, or the entity won't be created at all:
 * https://stackoverflow.com/questions/59362586/adding-conditional-to-an-existing-spring-annotation-in-spring-boot
 */
@Configuration
@PostgresFloat
public class EnablePostgresFloat {

}
