package org.octri.omop_annotator.domain.omop;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
 * OMOP 5.3 Definition of a Procedure Occurrence
 * 
 * The following columns have been excluded:
 * 	
 * 	procedure_date
 * 	modifier_concept_id
 * 	provider_id
 * 	visit_detail_id - Always 0
 *	procedure_source_value - For example, an ICD10 code, but without the vocabulary so not very useful?
 *	procedure_source_concept_id
 *	modifier_source_value
 * 
 */
@Entity
public class ProcedureOccurrence {
	
	@Column(name="procedure_occurrence_id")
	@Id
	private Long id;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="person_id")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name="procedure_concept_id")
	private Concept procedure;
	
	@Column(name="procedure_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date procedureDatetime;
	
	@ManyToOne
	@JoinColumn(name="procedure_type_concept_id")
	private Concept procedureType;
	
	@ManyToOne
	@JoinColumn(name="visit_occurrence_id")
	VisitOccurrence visitOccurrence;

	@Column(name="quantity")
	private BigDecimal quantity;

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

	public Concept getProcedure() {
		return procedure;
	}

	public void setProcedure(Concept procedure) {
		this.procedure = procedure;
	}

	public Date getProcedureDatetime() {
		return procedureDatetime;
	}

	public void setProcedureDatetime(Date procedureDatetime) {
		this.procedureDatetime = procedureDatetime;
	}

	public Concept getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(Concept procedureType) {
		this.procedureType = procedureType;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ProcedureOccurrence [id=" + id + ", person=" + person + ", procedure=" + procedure
				+ ", procedureDatetime=" + procedureDatetime + ", procedureType=" + procedureType + ", visitOccurrence="
				+ visitOccurrence + ", quantity=" + quantity + "]";
	}
	
}
