package org.octri.omop_annotator.domain.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.octri.omop_annotator.view.Labelled;

/**
 * Criteria against which a User judges an entry.
 * Ex. 'Women age 50-74 years who have had breast cancer screening with mammogram.'
 */
@Entity
public class Topic extends AbstractEntity implements Labelled {

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

	@Override
	public String getLabel() {
		return getTopicSet().getLabel() + ": " + getTopicNumber();
	}

}
