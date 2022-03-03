package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "visit_occurrence")
public interface VisitOccurrenceRepository extends PagingAndSortingRepository<VisitOccurrence, Long> {
	List<VisitOccurrence> findByPersonId(Long id);
}
