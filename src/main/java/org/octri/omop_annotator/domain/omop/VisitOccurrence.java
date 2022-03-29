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
 * provider_id
 * care_site_id
 * visit_type_concept_id (Generally "Visit derived from EHR encounter record")
 * visit_source_concept_id
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
	private Long id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	Person person;

	@ManyToOne
	@JoinColumn(name = "visit_concept_id")
	Concept visitType;

	@Column(name = "visit_start_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitStart;

	@Column(name = "visit_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitEnd;

	@Column(name = "visit_source_value")
	private String visitSource;

	@Column(name = "admitting_source_value")
	private String admittingSource;

	@Column(name = "discharge_to_source_value")
	private String dischargedTo;

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

	public Concept getVisitType() {
		return visitType;
	}

	public void setVisitType(Concept visitType) {
		this.visitType = visitType;
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

	public String getVisitSource() {
		return visitSource;
	}

	public void setVisitSource(String visitSource) {
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
		return "VisitOccurrence [id=" + id + ", person=" + person + ", visitType=" + visitType + ", visitStart="
				+ visitStart + ", visitEnd=" + visitEnd + ", visitSource=" + visitSource + ", admittingSource="
				+ admittingSource + ", dischargedTo=" + dischargedTo + "]";
	}

}
