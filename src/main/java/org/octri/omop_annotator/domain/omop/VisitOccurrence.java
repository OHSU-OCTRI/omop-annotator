package org.octri.omop_annotator.domain.omop;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;

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
@Indexed(index = "visit")
public class VisitOccurrence {

	@Id
	@Column(name = "visit_occurrence_id")
	private Integer id;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	Person person;

	@ManyToOne
	@JoinColumn(name = "visit_concept_id")
	Concept visitType;

	@FullTextField
	@Column(name = "visit_source_value")
	private String visitSourceValue;

	@Column(name = "visit_start_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitStart;

	@Column(name = "visit_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitEnd;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@ManyToOne
	@JoinColumn(name = "provider_id")
	Provider provider;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
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

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@OneToMany
	@JoinColumn(name = "visit_occurrence_id")
	private Set<ConditionOccurrence> conditionOccurrences;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@OneToMany
	@JoinColumn(name = "visit_occurrence_id")
	private Set<Observation> observations;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@OneToMany
	@JoinColumn(name = "visit_occurrence_id")
	private Set<ProcedureOccurrence> procedureOccurrences;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@OneToMany
	@JoinColumn(name = "visit_occurrence_id")
	private Set<Measurement> measurements;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@OneToMany
	@JoinColumn(name = "visit_occurrence_id")
	private Set<Note> notes;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@OneToMany
	@JoinColumn(name = "visit_occurrence_id")
	private Set<DrugExposure> drugExposures;

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

	public Set<ConditionOccurrence> getConditionOccurrences() {
		return conditionOccurrences;
	}

	public void setConditionOccurrences(Set<ConditionOccurrence> conditionOccurrences) {
		this.conditionOccurrences = conditionOccurrences;
	}

	public Set<Observation> getObservations() {
		return observations;
	}

	public void setObservations(Set<Observation> observations) {
		this.observations = observations;
	}

	public Set<ProcedureOccurrence> getProcedureOccurrences() {
		return procedureOccurrences;
	}

	public void setProcedureOccurrences(Set<ProcedureOccurrence> procedureOccurrences) {
		this.procedureOccurrences = procedureOccurrences;
	}

	public Set<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(Set<Measurement> measurements) {
		this.measurements = measurements;
	}

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	public Set<DrugExposure> getDrugExposures() {
		return drugExposures;
	}

	public void setDrugExposures(Set<DrugExposure> drugExposures) {
		this.drugExposures = drugExposures;
	}

	@Override
	public String toString() {
		return "VisitOccurrence [admittingSource=" + admittingSource + ", careSite=" + careSite + ", dischargedTo="
				+ dischargedTo + ", id=" + id + ", person=" + person + ", provider=" + provider + ", visitEnd="
				+ visitEnd + ", visitSource=" + visitSource + ", visitSourceValue=" + visitSourceValue + ", visitStart="
				+ visitStart + ", visitType=" + visitType + "]";
	}
}
