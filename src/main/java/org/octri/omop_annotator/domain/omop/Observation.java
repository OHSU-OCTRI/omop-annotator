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

import org.hibernate.annotations.Type;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;

/**
 * OMOP 5.3 Definition of an Observation
 *
 * The following columns have been excluded:
 *
 * observation_date - Duplicative of datetime
 * provider_id - Also on Visit
 * visit_detail_id - Always null at OHSU
 */
@Entity
public class Observation {

	@Column(name = "observation_id")
	@Id
	private Integer id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	private Person person;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@ManyToOne
	@JoinColumn(name = "observation_concept_id")
	private Concept observation;

	@Column(name = "observation_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date observationDatetime;

	@ManyToOne
	@JoinColumn(name = "observation_type_concept_id")
	private Concept observationType;

	@ManyToOne
	@JoinColumn(name = "observation_source_concept_id")
	private Concept observationSource;

	@Column(name = "observation_source_value")
	private String observationSourceValue;

	@Column(name = "value_as_number")
	@Type(type = "ToFloat")
	private Float valueAsNumber;

	@Column(name = "value_as_string")
	private String valueAsString;

	@ManyToOne
	@JoinColumn(name = "value_as_concept_id")
	private Concept valueAsConcept;

	@ManyToOne
	@JoinColumn(name = "qualifier_concept_id")
	private Concept qualifier;

	@Column(name = "qualifier_source_value")
	private String qualifierSourceValue;

	@ManyToOne
	@JoinColumn(name = "unit_concept_id")
	private Concept unit;

	@Column(name = "unit_source_value")
	private String unitSourceValue;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Concept getObservationSource() {
		return observationSource;
	}

	public void setObservationSource(Concept observationSource) {
		this.observationSource = observationSource;
	}

	public String getObservationSourceValue() {
		return observationSourceValue;
	}

	public void setObservationSourceValue(String observationSourceValue) {
		this.observationSourceValue = observationSourceValue;
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

	public String getQualifierSourceValue() {
		return qualifierSourceValue;
	}

	public void setQualifierSourceValue(String qualifierSourceValue) {
		this.qualifierSourceValue = qualifierSourceValue;
	}

	public Concept getUnit() {
		return unit;
	}

	public void setUnit(Concept unit) {
		this.unit = unit;
	}

	public String getUnitSourceValue() {
		return unitSourceValue;
	}

	public void setUnitSourceValue(String unitSourceValue) {
		this.unitSourceValue = unitSourceValue;
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
				+ ", observationSource=" + observationSource + ", observationSourceValue=" + observationSourceValue
				+ ", valueAsNumber=" + valueAsNumber + ", valueAsString=" + valueAsString + ", valueAsConcept="
				+ valueAsConcept + ", qualifier=" + qualifier + ", qualifierSourceValue=" + qualifierSourceValue
				+ ", unit=" + unit + ", unitSourceValue=" + unitSourceValue + ", visitOccurrence=" + visitOccurrence
				+ "]";
	}

}
