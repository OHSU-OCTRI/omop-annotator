package org.octri.omop_annotator.view;

import java.util.Date;

/**
 * Row in the Notes table.
 */
public interface NoteRow {

    public Long getId();

    public Long getPerson();

    public Date getDate();

    public String getType();

    public String getNoteClass();

    public String getTitle();

    public String getText();

    public Long getVisitOccurrence();
}
