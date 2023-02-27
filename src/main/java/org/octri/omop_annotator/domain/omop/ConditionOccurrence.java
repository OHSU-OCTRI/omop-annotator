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

import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;

/**
 * OMOP 5.3 Definition of a Condition Occurrence
 *
 * The following columns have been excluded:
 *
 * condition_start_date - Duplicative of datetime
 * condition_end_date - Duplicative of datetime
 * provider_id - Also on visit
 * visit_detail_id - Always null at OHSU
 */
@Entity
public class ConditionOccurrence {

	@Column(name = "condition_occurrence_id")
	@Id
	private Integer id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	private Person person;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@ManyToOne
	@JoinColumn(name = "condition_concept_id")
	private Concept condition;

	@Column(name = "condition_start_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date conditionStart;

	@Column(name = "condition_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date conditionEnd;

	@ManyToOne
	@JoinColumn(name = "condition_type_concept_id")
	private Concept conditionType;

	@ManyToOne
	@JoinColumn(name = "condition_source_concept_id")
	private Concept conditionSource;

	@Column(name = "condition_source_value")
	private String conditionSourceValue;

	@ManyToOne
	@JoinColumn(name = "condition_status_concept_id")
	private Concept conditionStatus;

	@Column(name = "condition_status_source_value")
	private String conditionStatusSourceValue;

	@Column(name = "stop_reason")
	private String stopReason;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id", insertable = false, updatable = false)
	VisitOccurrence visitOccurrence;

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

	public Concept getCondition() {
		return condition;
	}

	public void setCondition(Concept condition) {
		this.condition = condition;
	}

	public Date getConditionStart() {
		return conditionStart;
	}

	public void setConditionStart(Date conditionStart) {
		this.conditionStart = conditionStart;
	}

	public Date getConditionEnd() {
		return conditionEnd;
	}

	public void setConditionEnd(Date conditionEnd) {
		this.conditionEnd = conditionEnd;
	}

	public Concept getConditionType() {
		return conditionType;
	}

	public void setConditionType(Concept conditionType) {
		this.conditionType = conditionType;
	}

	public Concept getConditionSource() {
		return conditionSource;
	}

	public void setConditionSource(Concept conditionSource) {
		this.conditionSource = conditionSource;
	}

	public String getConditionSourceValue() {
		return conditionSourceValue;
	}

	public void setConditionSourceValue(String conditionSourceValue) {
		this.conditionSourceValue = conditionSourceValue;
	}

	public Concept getConditionStatus() {
		return conditionStatus;
	}

	public void setConditionStatus(Concept conditionStatus) {
		this.conditionStatus = conditionStatus;
	}

	public String getConditionStatusSourceValue() {
		return conditionStatusSourceValue;
	}

	public void setConditionStatusSourceValue(String conditionStatusSourceValue) {
		this.conditionStatusSourceValue = conditionStatusSourceValue;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}

	@Override
	public String toString() {
		return "ConditionOccurrence [id=" + id + ", person=" + person + ", condition=" + condition + ", conditionStart="
				+ conditionStart + ", conditionEnd=" + conditionEnd + ", conditionType=" + conditionType
				+ ", conditionSource=" + conditionSource + ", conditionSourceValue=" + conditionSourceValue
				+ ", conditionStatus=" + conditionStatus + ", conditionStatusSourceValue=" + conditionStatusSourceValue
				+ ", stopReason=" + stopReason + ", visitOccurrence=" + visitOccurrence + "]";
	}

}
