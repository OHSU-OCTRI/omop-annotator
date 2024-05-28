package org.octri.omop_annotator.domain.app;

import org.octri.authentication.server.security.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A piece of data that is pinned by the User on the PoolEntry.
 *
 */
@Entity
public class Pin extends AbstractEntity {

	@ManyToOne
	@NotNull
	private User user;

	@ManyToOne
	@NotNull
	private PoolEntry poolEntry;

	@Enumerated(value = EnumType.STRING)
	private OmopEntity entity;

	private Long entityId;

	private Long visitId;

	@Column(length = 500)
	@Size(max = 500)
	private String comment;

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

	public OmopEntity getEntity() {
		return entity;
	}

	public void setEntity(OmopEntity entity) {
		this.entity = entity;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Long getVisitId() {
		return visitId;
	}

	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Pin [user=" + user + ", poolEntry=" + poolEntry + ", entity=" + entity + ", entityId=" + entityId
				+ ", visitId=" + visitId + ", comment=" + comment + "]";
	}

}
