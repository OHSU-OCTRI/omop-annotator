package org.octri.omop_annotator.domain.omop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.octri.omop_annotator.view.Named;

/**
 * OMOP 5.3 Definition of a Concept
 *
 * The following columns have been excluded:
 *
 * domain_id
 * vocabulary_id
 * concept_class_id
 * standard_concept
 * valid_start_date
 * valid_end_date
 * invalid_reason
 */
@Entity
public class Concept implements Named {

	@Id
	@Column(name = "concept_id")
	private Integer id;

	@FullTextField
	@Column(name = "concept_name")
	private String name;

	@Column(name = "concept_code")
	private String code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Concept [id=" + id + ", name=" + name + ", code=" + code + "]";
	}

}
