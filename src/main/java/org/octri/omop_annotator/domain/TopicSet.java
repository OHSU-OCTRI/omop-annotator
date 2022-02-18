package org.octri.omop_annotator.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.octri.omop_annotator.view.Labelled;

@Entity
public class TopicSet extends AbstractEntity implements Labelled {

	private static final long serialVersionUID = 6598088013824057675L;

	@NotNull
	@Column(unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getLabel() {
		return getName();
	}
}
