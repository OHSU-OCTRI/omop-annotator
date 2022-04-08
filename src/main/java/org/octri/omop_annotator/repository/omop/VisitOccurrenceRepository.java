package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.octri.omop_annotator.view.VisitOccurrenceRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "visit_occurrence")
public interface VisitOccurrenceRepository extends PagingAndSortingRepository<VisitOccurrence, Long> {

	static final String visitOccurrenceQuery = "select visit_occurrence_id as id, person_id as person, visit_concept.concept_name as visitType, visit_start_datetime as visitStart, visit_end_datetime as visitEnd, provider.provider_name as providerName, care_site.care_site_name as careSiteName"
			+ " from visit_occurrence"
			+ " left join concept visit_concept on visit_concept.concept_id = visit_occurrence.visit_concept_id"
			+ " left join provider on provider.provider_id = visit_occurrence.provider_id"
			+ " left join care_site on care_site.care_site_id = visit_occurrence.care_site_id"
			+ " where visit_occurrence.person_id = ?1";

	List<VisitOccurrence> findByPersonId(Long id);

	// TODO: OA-95 ; Eliminate native queries for distribution to other organizations.
	@Query(value = visitOccurrenceQuery, nativeQuery = true)
	List<VisitOccurrenceRow> visitOccurrenceRows(Long personId);
}
