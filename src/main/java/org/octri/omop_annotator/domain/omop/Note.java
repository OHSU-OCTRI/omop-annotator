package org.octri.omop_annotator.domain.omop;

import java.util.Date;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

/**
 * OMOP 5.3 Definition of a Note
 *
 * The following columns have been excluded:
 *
 * note_date - Duplicative of date/time
 * encoding_concept_id
 * language_concept_id
 * provider_id - Also on visit
 * visit_detail_id - Null at OHSU
 */
@Entity
public class Note {

	@Column(name = "note_id")
	@Id
	private Integer id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "person_id")
	private Person person;

	@Column(name = "note_datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date noteDatetime;

	@ManyToOne
	@JoinColumn(name = "note_type_concept_id")
	private Concept noteType;

	@ManyToOne
	@JoinColumn(name = "note_class_concept_id")
	private Concept noteClass;

	@Column(name = "note_title")
	private String title;

	@FullTextField
	@Column(name = "note_text", length = Integer.MAX_VALUE)
	private String text;

	@Column(name = "note_source_value")
	private String noteSourceValue;

	@ManyToOne
	@JoinColumn(name = "visit_occurrence_id")
	private VisitOccurrence visitOccurrence;

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

	public Date getNoteDatetime() {
		return noteDatetime;
	}

	public void setNoteDatetime(Date noteDatetime) {
		this.noteDatetime = noteDatetime;
	}

	public Concept getNoteType() {
		return noteType;
	}

	public void setNoteType(Concept noteType) {
		this.noteType = noteType;
	}

	public Concept getNoteClass() {
		return noteClass;
	}

	public void setNoteClass(Concept noteClass) {
		this.noteClass = noteClass;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNoteSourceValue() {
		return noteSourceValue;
	}

	public void setNoteSourceValue(String noteSourceValue) {
		this.noteSourceValue = noteSourceValue;
	}

	public VisitOccurrence getVisitOccurrence() {
		return visitOccurrence;
	}

	public void setVisitOccurrence(VisitOccurrence visitOccurrence) {
		this.visitOccurrence = visitOccurrence;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", person=" + person + ", noteDatetime=" + noteDatetime + ", noteType=" + noteType
				+ ", noteClass=" + noteClass + ", title=" + title + ", text=" + text + ", noteSourceValue="
				+ noteSourceValue + ", visitOccurrence=" + visitOccurrence + "]";
	}

}
