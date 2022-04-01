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

import org.octri.omop_annotator.view.IdSerializer;
import org.octri.omop_annotator.view.NameSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
	@JsonSerialize(using = IdSerializer.class)
	private Person person;
	
	@ManyToOne
	@JoinColumn(name="condition_concept_id")
	@JsonSerialize(using = NameSerializer.class)
	private Concept condition;
	
	@Column(name="condition_start_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date conditionStart;
	
	@Column(name="condition_end_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date conditionEnd;
	
	@ManyToOne
	@JoinColumn(name="condition_type_concept_id")
	@JsonSerialize(using = NameSerializer.class)
	private Concept conditionType;
	
	@ManyToOne
	@JoinColumn(name="visit_occurrence_id")
	@JsonSerialize(using = IdSerializer.class)
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
