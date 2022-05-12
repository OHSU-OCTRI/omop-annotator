package org.octri.omop_annotator.domain.omop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * OMOP 5.3 Definition of a Provider
 * 
 * The following columns have been excluded:
 * 
 * npi
 * dea
 * care_site_id (Already given in visit_occurrence, so this is likely superfluous)
 * year_of_birth
 * gender_concept_id
 * provider_source_value
 * specialty_source_value
 * specialty_source_concept_id
 * gender_source_value
 * gender_source_concept_id
 */
@Entity
public class Provider {

	@Column(name = "provider_id")
	@Id
	private Integer id;

	@Column(name = "provider_name")
	private String providerName;

	@ManyToOne
	@JoinColumn(name = "specialty_concept_id")
	private Concept specialty;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Concept getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Concept specialty) {
		this.specialty = specialty;
	}

	@Override
	public String toString() {
		return "Provider [id=" + id + ", providerName=" + providerName + ", specialty=" + specialty + "]";
	}

}
