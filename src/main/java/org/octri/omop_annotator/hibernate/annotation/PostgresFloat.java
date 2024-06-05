package org.octri.omop_annotator.hibernate.annotation;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.annotations.JavaTypeRegistration;
import org.hibernate.type.descriptor.java.BigDecimalJavaType;

/**
 * A conditional conversion of Jdbc Big Decimal to Java Float for Postgres databases.
 * TODO: Make this work or remove.
 */
@Target({ PACKAGE, TYPE })
@Retention(RUNTIME)
// @ConditionalOnProperty(value = "omop.database", havingValue = "postgres")
@JavaTypeRegistration(javaType = Float.class, descriptorClass = BigDecimalJavaType.class)
public @interface PostgresFloat {
}