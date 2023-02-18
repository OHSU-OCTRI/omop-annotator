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

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
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

	@Column(name = "quantity")
	@Type(type = "ToFloat")
	private Float quantity;

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

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
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
		return "DrugExposure [doseUnit=" + doseUnit + ", drug=" + drug + ", drugEnd=" + drugEnd + ", drugStart="
				+ drugStart + ", drugType=" + drugType + ", id=" + id + ", person=" + person + ", quantity=" + quantity
				+ ", route=" + route + ", stopReason=" + stopReason + ", visitOccurrence=" + visitOccurrence + "]";
	}

}
