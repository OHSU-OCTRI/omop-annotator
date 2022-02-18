package org.octri.omop_annotator.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Topic extends AbstractEntity {

	private static final long serialVersionUID = 7416157520852431199L;

	@ManyToOne
	@NotNull
	private TopicSet topicSet;

	@NotNull
	private Integer topicNumber;

	@Column(columnDefinition = "TEXT")
	private String narrative;

	public TopicSet getTopicSet() {
		return topicSet;
	}

	public void setTopicSet(TopicSet topicSet) {
		this.topicSet = topicSet;
	}

	public Integer getTopicNumber() {
		return topicNumber;
	}

	public void setTopicNumber(Integer topicNumber) {
		this.topicNumber = topicNumber;
	}

	public String getNarrative() {
		return narrative;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
}
