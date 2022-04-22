package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.Note;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "note")
public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {

	List<Note> findByPersonId(Long id);

	List<Note> findByVisitOccurrenceId(Long visitOccurrenceId);

}
