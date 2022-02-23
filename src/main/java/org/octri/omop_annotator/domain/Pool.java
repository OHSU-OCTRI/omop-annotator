package org.octri.omop_annotator.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.octri.omop_annotator.view.Labelled;

/**
 * A collection of entries for judgment. Entries will be labelled with one of the annotations in the given
 * {@link AnnotationSchema}.
 *
 */
@Entity
public class Pool extends AbstractEntity implements Labelled {

	private static final long serialVersionUID = -2446082713990487107L;

	@ManyToOne
	@NotNull
	private TopicSet topicSet;

	@ManyToOne
	@NotNull
	private AnnotationSchema annotationSchema;

	@NotNull
	private String name;

	@Column(columnDefinition = "TEXT")
	private String comments;

	public TopicSet getTopicSet() {
		return topicSet;
	}

	public void setTopicSet(TopicSet topicSet) {
		this.topicSet = topicSet;
	}

	public AnnotationSchema getAnnotationSchema() {
		return annotationSchema;
	}

	public void setAnnotationSchema(AnnotationSchema annotationSchema) {
		this.annotationSchema = annotationSchema;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String getLabel() {
		return getName();
	}

}
