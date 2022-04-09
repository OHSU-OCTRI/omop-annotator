package org.octri.omop_annotator.view;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Projection Interface which defines a row in the ConditionList table (Vue component). This is a closed projection
 * which allows Spring Data JPA to optimize database queries.
 * See: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
 */
public interface ConditionOccurrenceRow {

	public Long getId();

	@JsonSerialize(using = IdSerializer.class)
	public IdOnly getPerson();

	@JsonSerialize(using = NameSerializer.class)
	public NameOnly getCondition();

	@JsonSerialize(using = NameSerializer.class)
	public NameOnly getConditionType();

	public Date getConditionStart();

	public Date getConditionEnd();

	@JsonSerialize(using = IdSerializer.class)
	public IdOnly getVisitOccurrence();

	/**
	 * Recursive projection for only including the id property.
	 */
	interface IdOnly extends Identified {

		public Long getId();
	}

	/**
	 * Recursive projection for only including the name property.
	 */
	interface NameOnly extends Named {

		public String getName();
	}
}
