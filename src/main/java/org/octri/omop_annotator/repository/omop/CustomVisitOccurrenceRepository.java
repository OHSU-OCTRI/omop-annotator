package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.VisitOccurrence;

/**
 * Defines a custom repository interface for full text search.
 */
public interface CustomVisitOccurrenceRepository {

    public List<VisitOccurrence> search(Integer personId, String term);
}
