package org.octri.omop_annotator.domain.app;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Configure the displays for the Omop Entities. The fieldName corresponds to the getters
 * in the interface projection (e.g., ConditionOccurrenceRow)
 */
@Entity
public class OmopDisplayConfiguration extends AbstractEntity {

    private String entityName;
    private String fieldName;
    private String columnDisplay;

    @Column(columnDefinition = "bit default 1")
    private boolean visible;

    @Column(columnDefinition = "bit default 0")
    private boolean filter;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
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

}
