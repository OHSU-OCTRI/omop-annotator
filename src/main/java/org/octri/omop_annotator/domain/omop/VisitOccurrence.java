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
 * OMOP 5.3 Definition of a Visit Occurrence
 * 
 * The following columns have been excluded:
 * 
 * visit_start_date
 * visit_end_date
 * visit_type_concept_id (Generally "Visit derived from EHR encounter record")
 * admitting_source_concept_id
 * discharge_to_concept_id
 * preceding_visit_occurrence_id
 * 
 * @author yateam
 *
 */
@Entity
public class VisitOccurrence {

	@Id
	@Column(name = "visit_occurrence_id")
	private Integer id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	Person person;

	@ManyToOne
	@JoinColumn(name = "visit_concept_id")
	Concept visitType;

	@Column(name = "visit_source_value")
	private String visitSourceValue;

	@Column(name = "visit_start_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitStart;

	@Column(name = "visit_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitEnd;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	Provider provider;

	@ManyToOne
	@JoinColumn(name = "care_site_id")
	CareSite careSite;

	// At OHSU, this always maps to "No matching concept". The source value has more info, but is not very user
	// friendly.
	@ManyToOne
	@JoinColumn(name = "visit_source_concept_id")
	private Concept visitSource;

	@Column(name = "admitting_source_value")
	private String admittingSource;

	@Column(name = "discharge_to_source_value")
	private String dischargedTo;

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

	public Concept getVisitType() {
		return visitType;
	}

	public void setVisitType(Concept visitType) {
		this.visitType = visitType;
	}

	public String getVisitSourceValue() {
		return visitSourceValue;
	}

	public void setVisitSourceValue(String visitSourceValue) {
		this.visitSourceValue = visitSourceValue;
	}

	public Date getVisitStart() {
		return visitStart;
	}

	public void setVisitStart(Date visitStart) {
		this.visitStart = visitStart;
	}

	public Date getVisitEnd() {
		return visitEnd;
	}

	public void setVisitEnd(Date visitEnd) {
		this.visitEnd = visitEnd;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public CareSite getCareSite() {
		return careSite;
	}

	public void setCareSite(CareSite careSite) {
		this.careSite = careSite;
	}

	public Concept getVisitSource() {
		return visitSource;
	}

	public void setVisitSource(Concept visitSource) {
		this.visitSource = visitSource;
	}

	public String getAdmittingSource() {
		return admittingSource;
	}

	public void setAdmittingSource(String admittingSource) {
		this.admittingSource = admittingSource;
	}

	public String getDischargedTo() {
		return dischargedTo;
	}

	public void setDischargedTo(String dischargedTo) {
		this.dischargedTo = dischargedTo;
	}

	@Override
	public String toString() {
		return "VisitOccurrence [admittingSource=" + admittingSource + ", careSite=" + careSite + ", dischargedTo="
				+ dischargedTo + ", id=" + id + ", person=" + person + ", provider=" + provider + ", visitEnd="
				+ visitEnd + ", visitSource=" + visitSource + ", visitSourceValue=" + visitSourceValue + ", visitStart="
				+ visitStart + ", visitType=" + visitType + "]";
	}

}
