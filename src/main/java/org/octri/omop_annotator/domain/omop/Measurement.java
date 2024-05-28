package org.octri.omop_annotator.domain.omop;

import java.util.Date;

import org.hibernate.annotations.Type;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

/**
 * OMOP 5.3 Definition of a Measurement
 *
 * The following columns have been excluded:
 *
 * measurement_date - Duplicative of datetime
 * measurement_time - Duplicative of datetime
 * provider_id - Also on visit
 * visit_detail_id - Null at OHSU
 */
@Entity
public class Measurement {

	@Column(name = "measurement_id")
	@Id
	private Integer id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	private Person person;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@ManyToOne
	@JoinColumn(name = "measurement_concept_id")
	private Concept measurement;

	@Column(name = "measurement_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date measurementDatetime;

	@ManyToOne
	@JoinColumn(name = "measurement_type_concept_id")
	private Concept measurementType;

	@ManyToOne
	@JoinColumn(name = "operator_concept_id")
	private Concept measurementOperator;

	@Column(name = "value_source_value")
	private String valueSourceValue;

	@Column(name = "value_as_number")
	@Type(type = "ToFloat")
	private Float valueAsNumber;

	@ManyToOne
	@JoinColumn(name = "value_as_concept_id")
	private Concept valueAsConcept;

	@ManyToOne
	@JoinColumn(name = "unit_concept_id")
	private Concept unit;

	@Column(name = "unit_source_value")
	private String unitSourceValue;

	@Column(name = "range_low")
	@Type(type = "ToFloat")
	private Float rangeLow;

	@Column(name = "range_high")
	@Type(type = "ToFloat")
	private Float rangeHigh;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	// At OHSU, this seems to be identical to measurement
	@ManyToOne
	@JoinColumn(name = "measurement_source_concept_id")
	private Concept measurementSource;

	@Column(name = "measurement_source_value")
	private String measurementSourceValue;

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

	public Concept getMeasurementOperator() {
		return measurementOperator;
	}

	public void setMeasurementOperator(Concept measurementOperator) {
		this.measurementOperator = measurementOperator;
	}

	public String getValueSourceValue() {
		return valueSourceValue;
	}

	public void setValueSourceValue(String valueSourceValue) {
		this.valueSourceValue = valueSourceValue;
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

	public String getUnitSourceValue() {
		return unitSourceValue;
	}

	public void setUnitSourceValue(String unitSourceValue) {
		this.unitSourceValue = unitSourceValue;
	}

	public Float getRangeLow() {
		return rangeLow;
	}

	public void setRangeLow(Float rangeLow) {
		this.rangeLow = rangeLow;
	}

	public Float getRangeHigh() {
		return rangeHigh;
	}

	public void setRangeHigh(Float rangeHigh) {
		this.rangeHigh = rangeHigh;
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

	public String getMeasurementSourceValue() {
		return measurementSourceValue;
	}

	public void setMeasurementSourceValue(String measurementSourceValue) {
		this.measurementSourceValue = measurementSourceValue;
	}

	@Override
	public String toString() {
		return "Measurement [id=" + id + ", person=" + person + ", measurement=" + measurement
				+ ", measurementDatetime=" + measurementDatetime + ", measurementType=" + measurementType
				+ ", measurementOperator=" + measurementOperator + ", valueSourceValue=" + valueSourceValue
				+ ", valueAsNumber=" + valueAsNumber + ", valueAsConcept=" + valueAsConcept + ", unit=" + unit
				+ ", unitSourceValue=" + unitSourceValue + ", rangeLow=" + rangeLow + ", rangeHigh=" + rangeHigh
				+ ", visitOccurrence=" + visitOccurrence + ", measurementSource=" + measurementSource
				+ ", measurementSourceValue=" + measurementSourceValue + "]";
	}

}