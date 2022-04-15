package org.octri.omop_annotator.domain.app;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.octri.omop_annotator.view.Labelled;

/**
 * Label assigned to a {@link PoolEntry} through a {@link Judgment}.
 *
 */
@Entity
public class AnnotationLabel extends AbstractEntity implements Labelled {

	private static final long serialVersionUID = -7698547499917439272L;

	@ManyToOne
	@NotNull
	private AnnotationSchema annotationSchema;

	@NotNull
	private Integer displayOrder;

	@Size(max = 7)
	@NotNull
	private String accentColor;

	@NotNull
	private String displayLabel;

	@NotNull
	private String outputLabel;

	public AnnotationSchema getAnnotationSchema() {
		return annotationSchema;
	}

	public void setAnnotationSchema(AnnotationSchema annotationSchema) {
		this.annotationSchema = annotationSchema;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getAccentColor() {
		return accentColor;
	}

	public void setAccentColor(String accentColor) {
		this.accentColor = accentColor;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	public String getOutputLabel() {
		return outputLabel;
	}

	public void setOutputLabel(String outputLabel) {
		this.outputLabel = outputLabel;
	}

	@Override
	public String getLabel() {
		return getDisplayLabel();
	}

}
