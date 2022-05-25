package org.octri.omop_annotator.auditing;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated type does not access any protected health information and should not be included in
 * access logs.
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Unaudited {

	/**
	 * Optional text describing why the type has been excluded from request logging.
	 *
	 * @return
	 */
	public String reason() default "";

}