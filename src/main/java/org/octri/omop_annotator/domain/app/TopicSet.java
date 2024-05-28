package org.octri.omop_annotator.domain.app;

import org.octri.omop_annotator.view.Labelled;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a collection of {@link Topic}s.
 */
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
