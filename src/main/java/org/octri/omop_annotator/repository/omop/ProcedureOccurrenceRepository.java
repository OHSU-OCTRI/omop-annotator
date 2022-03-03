package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.ProcedureOccurrence;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "procedure_occurrence")
public interface ProcedureOccurrenceRepository extends PagingAndSortingRepository<ProcedureOccurrence, Long> {
	List<ProcedureOccurrence> findByPersonId(Long id);
}
