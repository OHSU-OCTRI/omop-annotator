package org.octri.omop_annotator.domain.omop;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * OMOP 5.3 Definition of an Observation
 * 
 * The following columns have been excluded:
 * 
 * observation_date provider_id visit_detail_id observation_source_value
 * observation_source_concept_id unit_source_value qualifier_source_value
 */
@Entity
public class Observation {

	@Column(name = "observation_id")
	@Id
	private Long id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "observation_concept_id")
	private Concept observation;

	@Column(name = "observation_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date observationDatetime;

	@ManyToOne
	@JoinColumn(name = "observation_type_concept_id")
	private Concept observationType;

	@Column(name = "value_as_number")
	private Float valueAsNumber;

	@Column(name = "value_as_string")
	private String valueAsString;

	@ManyToOne
	@JoinColumn(name = "value_as_concept_id")
	private Concept valueAsConcept;

	@ManyToOne
	@JoinColumn(name = "qualifier_concept_id")
	private Concept qualifier;

	@ManyToOne
	@JoinColumn(name = "unit_concept_id")
	private Concept unit;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Concept getObservation() {
		return observation;
	}

	public void setObservation(Concept observation) {
		this.observation = observation;
	}

	public Date getObservationDatetime() {
		return observationDatetime;
	}

	public void setObservationDatetime(Date observationDatetime) {
		this.observationDatetime = observationDatetime;
	}

	public Concept getObservationType() {
		return observationType;
	}

	public void setObservationType(Concept observationType) {
		this.observationType = observationType;
	}

	public Float getValueAsNumber() {
		return valueAsNumber;
	}

	public void setValueAsNumber(Float valueAsNumber) {
		this.valueAsNumber = valueAsNumber;
	}

	public String getValueAsString() {
		return valueAsString;
	}

	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}

	public Concept getValueAsConcept() {
		return valueAsConcept;
	}

	public void setValueAsConcept(Concept valueAsConcept) {
		this.valueAsConcept = valueAsConcept;
	}

	public Concept getQualifier() {
		return qualifier;
	}

	public void setQualifier(Concept qualifier) {
		this.qualifier = qualifier;
	}

	public Concept getUnit() {
		return unit;
	}

	public void setUnit(Concept unit) {
		this.unit = unit;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}

	@Override
	public String toString() {
		return "Observation [id=" + id + ", person=" + person + ", observation=" + observation
				+ ", observationDatetime=" + observationDatetime + ", observationType=" + observationType
				+ ", valueAsNumber=" + valueAsNumber + ", valueAsString=" + valueAsString + ", valueAsConcept="
				+ valueAsConcept + ", qualifier=" + qualifier + ", unit=" + unit + ", visitOccurrence="
				+ visitOccurrence + "]";
	}

}
