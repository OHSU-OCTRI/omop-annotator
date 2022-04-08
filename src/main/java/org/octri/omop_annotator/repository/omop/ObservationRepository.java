package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.Observation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "observation")
public interface ObservationRepository extends PagingAndSortingRepository<Observation, Long> {

	List<Observation> findByPersonId(Long id);
	List<Observation> findByPersonIdAndVisitOccurrenceId(Long personId, Long visitOccurrenceId);

}
