package org.octri.omop_annotator.domain.omop;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * OMOP 5.3 Definition of a Condition Occurrence
 * 
 * The following columns have been excluded:
 * 
 *	condition_start_date
 *	condition_end_date
 *	stop_reason
 *	provider_id
 *	condition_status_concept_id
 *	visit_detail_id - Always null
 *	condition_source_value - Might be an ICD10 code, for example, but need full concept to know vocabulary
 *	condition_source_concept_id - Might want eventually, but not pulled in by Dr. Bedrick
 *	condition_status_source_value - Often null
 */
@Entity
public class ConditionOccurrence {
	
	@Column(name="condition_occurrence_id")
	@Id
	private Long id;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="person_id")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name="condition_concept_id")
	private Concept condition;
	
	@Column(name="condition_start_datetime")
	private Timestamp conditionStart;
	
	@Column(name="condition_end_datetime")
	private Timestamp conditionEnd;
	
	@ManyToOne
	@JoinColumn(name="condition_type_concept_id")
	private Concept conditionType;
	
	@ManyToOne
	@JoinColumn(name="visit_occurrence_id")
	VisitOccurrence visitOccurrence;

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

	public Concept getCondition() {
		return condition;
	}

	public void setCondition(Concept condition) {
		this.condition = condition;
	}

	public Timestamp getConditionStart() {
		return conditionStart;
	}

	public void setConditionStart(Timestamp conditionStart) {
		this.conditionStart = conditionStart;
	}

	public Timestamp getConditionEnd() {
		return conditionEnd;
	}

	public void setConditionEnd(Timestamp conditionEnd) {
		this.conditionEnd = conditionEnd;
	}

	public Concept getConditionType() {
		return conditionType;
	}

	public void setConditionType(Concept conditionType) {
		this.conditionType = conditionType;
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
				+ ", visitOccurrence=" + visitOccurrence + "]";
	}
	
}
