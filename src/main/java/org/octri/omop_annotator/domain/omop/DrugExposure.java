package org.octri.omop_annotator.domain.omop;

import java.util.Date;

import org.hibernate.annotations.JavaTypeRegistration;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;
import org.hibernate.type.descriptor.java.BigDecimalJavaType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

/**
 * OMOP 5.3 Definition of a Drug Exposure
 *
 * The following columns have been excluded:
 *
 * drug_exposure_start_date - Duplicative of date/time
 * drug_exposure_end_date - Duplicative of date/time
 * verbatim_end_date
 * sig
 * route_concept_id
 * lot_number
 * provider_id - Also on visit
 * visit_detail_id - always null
 */
@Entity
@JavaTypeRegistration(javaType = Float.class, descriptorClass = BigDecimalJavaType.class)
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

	@Column(name = "refills")
	private Integer refills;

	@Column(name = "quantity")
	private Float quantity;

	@Column(name = "days_supply")
	private Integer daysSupply;

	@ManyToOne
	@JoinColumn(name = "drug_type_concept_id")
	private Concept drugType;

	@ManyToOne
	@JoinColumn(name = "drug_source_concept_id")
	private Concept drugSource;

	@Column(name = "drug_source_value")
	private String drugSourceValue;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

	@Column(name = "route_source_value")
	private String routeSourceValue;

	@Column(name = "dose_unit_source_value")
	private String doseUnitSourceValue;

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

	public Integer getRefills() {
		return refills;
	}

	public void setRefills(Integer refills) {
		this.refills = refills;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Integer getDaysSupply() {
		return daysSupply;
	}

	public void setDaysSupply(Integer daysSupply) {
		this.daysSupply = daysSupply;
	}

	public Concept getDrugType() {
		return drugType;
	}

	public void setDrugType(Concept drugType) {
		this.drugType = drugType;
	}

	public Concept getDrugSource() {
		return drugSource;
	}

	public void setDrugSource(Concept drugSource) {
		this.drugSource = drugSource;
	}

	public String getDrugSourceValue() {
		return drugSourceValue;
	}

	public void setDrugSourceValue(String drugSourceValue) {
		this.drugSourceValue = drugSourceValue;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}

	public String getRouteSourceValue() {
		return routeSourceValue;
	}

	public void setRouteSourceValue(String routeSourceValue) {
		this.routeSourceValue = routeSourceValue;
	}

	public String getDoseUnitSourceValue() {
		return doseUnitSourceValue;
	}

	public void setDoseUnitSourceValue(String doseUnitSourceValue) {
		this.doseUnitSourceValue = doseUnitSourceValue;
	}

	@Override
	public String toString() {
		return "DrugExposure [id=" + id + ", person=" + person + ", drug=" + drug + ", drugStart=" + drugStart
				+ ", drugEnd=" + drugEnd + ", stopReason=" + stopReason + ", refills=" + refills + ", quantity="
				+ quantity + ", daysSupply=" + daysSupply + ", drugType=" + drugType + ", drugSource=" + drugSource
				+ ", drugSourceValue=" + drugSourceValue + ", visitOccurrence=" + visitOccurrence
				+ ", routeSourceValue=" + routeSourceValue + ", doseUnitSourceValue=" + doseUnitSourceValue + "]";
	}

}
