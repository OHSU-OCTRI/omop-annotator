package org.octri.omop_annotator.domain.app;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.octri.omop_annotator.view.Labelled;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a collection of annotation labels used when judging entries.
 */
@Entity
public class AnnotationSchema extends AbstractEntity implements Labelled {

	private static final long serialVersionUID = 7887594025584093096L;

	@NotNull
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
