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
 * OMOP 5.3 Definition of a Drug Exposure
 * 
 * The following columns have been excluded:
 * 
 * drug_exposure_start_date
 * drug_exposure_end_date
 * verbatim_end_date
 * refills
 * days_supply
 * sig
 * route_concept_id
 * lot_number
 * provider_id
 * visit_detail_id - always null
 * drug_source_value
 * drug_source_concept_id
 */
@Entity
public class DrugExposure {

	@Column(name = "drug_exposure_id")
	@Id
	private Integer id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "drug_concept_id")
	private Concept drug;

	@Column(name = "drug_exposure_start_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date drugStart;

	@Column(name = "drug_exposure_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date drugEnd;

	@Column(name = "stop_reason")
	private String stopReason;

	// TODO: Add this back with support for both Oracle and Postgres
	// @Column(name = "quantity")
	// private Float quantity;

	@ManyToOne
	@JoinColumn(name = "drug_type_concept_id")
	private Concept drugType;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	@Column(name = "route_source_value")
	private String route;

	@Column(name = "dose_unit_source_value")
	private String doseUnit;

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

	public Concept getDrug() {
		return drug;
	}

	public void setDrug(Concept drug) {
		this.drug = drug;
	}

	public Date getDrugStart() {
		return drugStart;
	}

	public void setDrugStart(Date drugStart) {
		this.drugStart = drugStart;
	}

	public Date getDrugEnd() {
		return drugEnd;
	}

	public void setDrugEnd(Date drugEnd) {
		this.drugEnd = drugEnd;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public Concept getDrugType() {
		return drugType;
	}

	public void setDrugType(Concept drugType) {
		this.drugType = drugType;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getDoseUnit() {
		return doseUnit;
	}

	public void setDoseUnit(String doseUnit) {
		this.doseUnit = doseUnit;
	}

	@Override
	public String toString() {
		return "DrugExposure [id=" + id + ", person=" + person + ", drug=" + drug + ", drugStart=" + drugStart
				+ ", drugEnd=" + drugEnd + ", stopReason=" + stopReason + ", drugType="
				+ drugType + ", visitOccurrence=" + visitOccurrence + ", route=" + route + ", doseUnit=" + doseUnit
				+ "]";
	}

}
