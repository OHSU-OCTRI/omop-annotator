package org.octri.omop_annotator.domain.omop;

import java.util.Date;

import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

/**
 * OMOP 5.3 Definition of a Procedure Occurrence
 *
 * The following columns have been excluded:
 *
 * procedure_date - Duplicative of datetime
 * provider_id - Also on visit
 * visit_detail_id - Always 0
 */
@Entity
public class ProcedureOccurrence {

	@Column(name = "procedure_occurrence_id")
	@Id
	private Integer id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	private Person person;

	@IndexedEmbedded
	@IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
	@ManyToOne
	@JoinColumn(name = "procedure_concept_id")
	private Concept procedure;

	@Column(name = "procedure_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date procedureDatetime;

	@ManyToOne
	@JoinColumn(name = "procedure_type_concept_id")
	private Concept procedureType;

	@ManyToOne
	@JoinColumn(name = "procedure_source_concept_id")
	private Concept procedureSource;

	@Column(name = "procedure_source_value")
	private String procedureSourceValue;

	@ManyToOne
	@JoinColumn(name = "modifier_concept_id")
	private Concept procedureModifier;

	@Column(name = "modifier_source_value")
	private String modifierSourceValue;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	VisitOccurrence visitOccurrence;

	@Column(name = "quantity")
	private Integer quantity;

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

	public Concept getProcedureSource() {
		return procedureSource;
	}

	public void setProcedureSource(Concept procedureSource) {
		this.procedureSource = procedureSource;
	}

	public String getProcedureSourceValue() {
		return procedureSourceValue;
	}

	public void setProcedureSourceValue(String procedureSourceValue) {
		this.procedureSourceValue = procedureSourceValue;
	}

	public Concept getProcedureModifier() {
		return procedureModifier;
	}

	public void setProcedureModifier(Concept procedureModifier) {
		this.procedureModifier = procedureModifier;
	}

	public String getModifierSourceValue() {
		return modifierSourceValue;
	}

	public void setModifierSourceValue(String modifierSourceValue) {
		this.modifierSourceValue = modifierSourceValue;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ProcedureOccurrence [id=" + id + ", person=" + person + ", procedure=" + procedure
				+ ", procedureDatetime=" + procedureDatetime + ", procedureType=" + procedureType + ", procedureSource="
				+ procedureSource + ", procedureSourceValue=" + procedureSourceValue + ", procedureModifier="
				+ procedureModifier + ", modifierSourceValue=" + modifierSourceValue + ", visitOccurrence="
				+ visitOccurrence + ", quantity=" + quantity + "]";
	}

}
