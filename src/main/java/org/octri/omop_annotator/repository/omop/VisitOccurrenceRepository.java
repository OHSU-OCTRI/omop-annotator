package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.octri.omop_annotator.view.VisitOccurrenceRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "visit_occurrence")
public interface VisitOccurrenceRepository extends PagingAndSortingRepository<VisitOccurrence, Long> {

	static final String visitOccurrenceQuery = "select v.id as id, v.person.id as person, visitType.name as visitType,"
			+ " v.visitStart as visitStart, v.visitEnd as visitEnd, provider.providerName as providerName, careSite.careSiteName as careSiteName"
			+ " from VisitOccurrence v"
			+ " left join v.visitType visitType"
			+ " left join v.provider provider"
			+ " left join v.careSite careSite"
			+ " where v.person.id = ?1";

	@Query(value = visitOccurrenceQuery)
	List<VisitOccurrenceRow> findByPersonId(Long id);
}
