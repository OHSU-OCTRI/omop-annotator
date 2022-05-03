package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.Note;
import org.octri.omop_annotator.view.NoteRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "note")
public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {

	static final String query = "select note.id as id, note.person.id as person,"
			+ " noteTypeConcept.name as type, note.noteDatetime as date, noteClassConcept.name as noteClass,"
			+ " note.title as title, note.text as text, visitOccurrence.id as visitOccurrence"
			+ " from Note note"
			+ " left join note.noteType noteTypeConcept"
			+ " left join note.noteClass noteClassConcept"
			+ " left join note.visitOccurrence visitOccurrence";

	@Query(query + " where note.person.id = ?1")
	List<NoteRow> findByPersonId(Long id);

	@Query(query + " where visitOccurrence.id = ?1")
	List<NoteRow> findByVisitOccurrenceId(Long visitOccurrenceId);

}
