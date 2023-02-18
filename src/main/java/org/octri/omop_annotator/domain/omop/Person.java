package org.octri.omop_annotator.domain.omop;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@Transient
	private LocalDate ageCalculationDate = LocalDate.now();

	/* GenericFields can be used for exact matches when searching with Hibernate Search. */
	@GenericField
	@Id
	@Column(name = "person_id")
	public Integer id;

	@Column(name = "birth_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date birthDatetime;

	@Column(name = "gender_source_value")
	private String gender;

	@Column(name = "race_source_value")
	private String race;

	@Column(name = "ethnicity_source_value")
	private String ethnicity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	/**
	 * Get the patient's age as of the ageCalculationDate.
	 *
	 * @return age in years
	 */
	public Integer getAge() {
		if (this.getBirthDatetime() == null) {
			return null;
		}
		LocalDate birthDate = Instant.ofEpochMilli(this.getBirthDatetime().getTime())
				.atZone(ZoneId.systemDefault())
				.toLocalDate();

		return Period.between(birthDate, this.ageCalculationDate).getYears();
	}

	/**
	 * Date at which the patient's age should be calculated. Result is a string that can be serialized to JSON.
	 *
	 * @return
	 */
	public String getAgeCalculationDate() {
		return DateTimeFormatter.ISO_LOCAL_DATE.format(this.ageCalculationDate);
	}

	public void setAgeCalculationDate(LocalDate ageCalculationDate) {
		this.ageCalculationDate = ageCalculationDate;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", birthDatetime=" + birthDatetime + ", gender=" + gender + ", race=" + race
				+ ", ethnicity=" + ethnicity + "]";
	}

}
