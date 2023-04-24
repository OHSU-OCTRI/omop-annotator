package org.octri.omop_annotator.domain.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Configure the displays for the Omop Entities. The fieldName corresponds to the getters
 * in the interface projection (e.g., ConditionOccurrenceRow)
 */
@Entity
public class OmopDisplayConfiguration extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "entity")
    private OmopEntity omopEntity;

    private String fieldName;
    private String columnDisplay;

    @Column(columnDefinition = "bit default 1")
    private boolean visible;

    @Column(columnDefinition = "bit default 0")
    private boolean filter;

    // Prevent the field from being edited through the UI in cases where application logic may be impacted
    @Column(columnDefinition = "bit default 1")
    private boolean editable;

    // Prevent the field from being filterable through the UI in cases where application logic may be impacted
    @Column(columnDefinition = "bit default 1")
    private boolean filterable;

    public OmopEntity getOmopEntity() {
        return omopEntity;
    }

    public void setOmopEntity(OmopEntity omopEntity) {
        this.omopEntity = omopEntity;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnDisplay() {
        return columnDisplay;
    }

    public void setColumnDisplay(String columnDisplay) {
        this.columnDisplay = columnDisplay;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean getFilterable() {
        return filterable;
    }

    public void setFilterable(boolean filterable) {
        this.filterable = filterable;
    }

}
