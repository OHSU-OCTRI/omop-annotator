package org.octri.omop_annotator.repository.omop;

import org.octri.omop_annotator.domain.omop.Concept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "concept")
public interface ConceptRepository extends CrudRepository<Concept, Integer> {
}
