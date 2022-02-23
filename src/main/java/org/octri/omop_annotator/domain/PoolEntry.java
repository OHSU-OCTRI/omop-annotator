package org.octri.omop_annotator.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.octri.omop_annotator.view.Labelled;

/**
 * Item in a {@link Pool} to be judged/annotated. Each entry has a given {@link Topic}, as well as an ordering for
 * display.
 *
 */
@Entity
public class PoolEntry extends AbstractEntity implements Labelled {

	private static final long serialVersionUID = 720834163855020712L;

	@ManyToOne
	@NotNull
	private Pool pool;

	@ManyToOne
	@NotNull
	private Topic topic;

	private Integer sortOrder;

	// TODO: related entity? does not appear in the prototype.
	private Integer documentId;

	private Integer frequency;

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	@Override
	public String getLabel() {
		// TODO: what is a good label for this entity?
		return toString();
	}

}
