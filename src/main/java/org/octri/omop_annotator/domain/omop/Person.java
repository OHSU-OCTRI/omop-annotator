package org.octri.omop_annotator.domain.omop;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OMOP 5.3 Definition of a Person 
 * 
 * The following columns have been excluded:
 * 
 * gender_concept_id
 * year_of_birth
 * month_of_birth
 * day_of_birth
 * race_concept_id
 * ethnicity_concept_id
 * location_id
 * provider_id
 * care_site_id
 * person_source_value
 * gender_source_concept_id
 * race_source_concept_id
 * ethnicity_source_concept_id
 */
@Entity
public class Person {
	
	@Id
	@Column(name = "person_id")
	public Long id;
	
	@Column(name = "birth_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthDatetime;
	
	@Column(name="gender_source_value")
	private String gender;

	@Column(name="race_source_value")
	private String race;

	@Column(name="ethnicity_source_value")
	private String ethnicity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBirthDatetime() {
		return birthDatetime;
	}

	public void setBirthDatetime(Date birthDatetime) {
		this.birthDatetime = birthDatetime;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", birthDatetime=" + birthDatetime + ", gender=" + gender + ", race=" + race
				+ ", ethnicity=" + ethnicity + "]";
	}

}
