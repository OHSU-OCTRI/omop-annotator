package org.octri.omop_annotator.view;

import org.octri.omop_annotator.domain.app.Pin;
import org.springframework.util.Assert;

/**
 * {@link Pin} representation for working with JSON and front end code.
 */
public class PinDTO {

    private Long id;
    private Long userId;
    private Long poolEntryId;
    private String entity;
    private Long entityId;
    private Long visitId;

    public PinDTO() {
    }

    /**
     * Initialize the DTO from a Pin object.
     * 
     * @param pin
     */
    public void initialize(Pin pin) {
        Assert.notNull(pin, "Pin is required");
        this.id = pin.getId();
        this.userId = pin.getUser().getId();
        this.poolEntryId = pin.getPoolEntry().getId();
        this.entity = pin.getEntity();
        this.entityId = pin.getEntityId();
        this.visitId = pin.getVisitId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPoolEntryId() {
        return poolEntryId;
    }

    public void setPoolEntryId(Long poolEntryId) {
        this.poolEntryId = poolEntryId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
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

}
