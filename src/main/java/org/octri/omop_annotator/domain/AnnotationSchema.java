package org.octri.omop_annotator.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.octri.omop_annotator.view.Labelled;

@Entity
public class AnnotationSchema extends AbstractEntity implements Labelled {

	private static final long serialVersionUID = 7887594025584093096L;

	@NotNull
	String name;

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
