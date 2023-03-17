package org.octri.omop_annotator.domain.app;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.octri.authentication.server.security.entity.User;

/**
 * An piece of data that is pinned by the User on the PoolEntry.
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

	@Override
	public String toString() {
		return "Pin [user=" + user.getId() + ", poolEntry=" + poolEntry.getId() + ", entity=" + entity + ", entityId="
				+ entityId
				+ ", visitId=" + visitId + "]";
	}

}
