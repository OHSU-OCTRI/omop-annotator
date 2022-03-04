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
 * OMOP 5.3 Definition of a Measurement
 * 
 * The following columns have been excluded:
 * 
 * 	measurement_date
 * 	measurement_time
 * 	operator_concept_id
 * 	range_low
 * 	range_high
 *	provider_id
 *	visit_detail_id
 *	measurement_source_value
 *	unit_source_value
 *	value_source_value
 * 
 */
@Entity
public class Measurement {

	@Column(name="measurement_id")
	@Id
	private Long id;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="person_id")
	private Person person;

	@ManyToOne
	@JoinColumn(name="measurement_concept_id")
	private Concept measurement;
	
	@Column(name="measurement_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date measurementDatetime;
	
	@ManyToOne
	@JoinColumn(name="measurement_type_concept_id")
	private Concept measurementType;
	
	@Column(name="value_as_number")
	private Float valueAsNumber;
	
	@ManyToOne
	@JoinColumn(name="value_as_concept_id")
	private Concept valueAsConcept;
	
	@ManyToOne
	@JoinColumn(name="unit_concept_id")
	private Concept unit;

	@ManyToOne
	@JoinColumn(name="visit_occurrence_id")
	private VisitOccurrence visitOccurrence;
	
	@ManyToOne
	@JoinColumn(name="measurement_source_concept_id")
	private Concept measurementSource;

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

	public Concept getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Concept measurement) {
		this.measurement = measurement;
	}

	public Date getMeasurementDatetime() {
		return measurementDatetime;
	}

	public void setMeasurementDatetime(Date measurementDatetime) {
		this.measurementDatetime = measurementDatetime;
	}

	public Concept getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(Concept measurementType) {
		this.measurementType = measurementType;
	}

	public Float getValueAsNumber() {
		return valueAsNumber;
	}

	public void setValueAsNumber(Float valueAsNumber) {
		this.valueAsNumber = valueAsNumber;
	}

	public Concept getValueAsConcept() {
		return valueAsConcept;
	}

	public void setValueAsConcept(Concept valueAsConcept) {
		this.valueAsConcept = valueAsConcept;
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

	public Concept getMeasurementSource() {
		return measurementSource;
	}

	public void setMeasurementSource(Concept measurementSource) {
		this.measurementSource = measurementSource;
	}

	@Override
	public String toString() {
		return "Measurement [id=" + id + ", person=" + person + ", measurement=" + measurement
				+ ", measurementDatetime=" + measurementDatetime + ", measurementType=" + measurementType
				+ ", valueAsNumber=" + valueAsNumber + ", valueAsConcept=" + valueAsConcept + ", unit=" + unit
				+ ", visitOccurrence=" + visitOccurrence + ", measurementSource=" + measurementSource + "]";
	}
	
}
