package org.octri.omop_annotator.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.octri.authentication.server.security.entity.User;

/**
 * Represents a User's judgment of a {@link PoolEntry}. The entry annotated by the User with an {@link AnnotationLabel}.
 *
 */
@Entity
public class Judgment extends AbstractEntity {

	private static final long serialVersionUID = -7918768470025483600L;

	@ManyToOne
	@NotNull
	private User user;

	@ManyToOne
	@NotNull
	private PoolEntry poolEntry;

	@ManyToOne
	private AnnotationLabel annotationLabel;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PoolEntry getPoolEntry() {
		return poolEntry;
	}

	public void setPoolEntry(PoolEntry poolEntry) {
		this.poolEntry = poolEntry;
	}

	public AnnotationLabel getAnnotationLabel() {
		return annotationLabel;
	}

	public void setAnnotationLabel(AnnotationLabel annotationLabel) {
		this.annotationLabel = annotationLabel;
	}

}
