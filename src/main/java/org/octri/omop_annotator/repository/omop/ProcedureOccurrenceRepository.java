package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.ProcedureOccurrence;
import org.octri.omop_annotator.view.ProcedureOccurrenceRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "procedure_occurrence")
public interface ProcedureOccurrenceRepository extends PagingAndSortingRepository<ProcedureOccurrence, Integer> {

	static final String query = "select po.id as id, po.person.id as personId, procedure.name as procedure,"
			+ " procedureType.name as procedureType, po.procedureDatetime as date, po.quantity as quantity,"
			+ " visitOccurrence.id as visitOccurrence"
			+ " from ProcedureOccurrence po"
			+ " left join po.procedure procedure"
			+ " left join po.procedureType procedureType"
			+ " left join po.visitOccurrence visitOccurrence";

	@Query(query + " where po.person.id = ?1")
	List<ProcedureOccurrenceRow> findByPersonId(Integer personId);

	@Query(query + " where visitOccurrence.id = ?1")
	List<ProcedureOccurrenceRow> findByVisitOccurrenceId(Integer visitOccurrenceId);

}
