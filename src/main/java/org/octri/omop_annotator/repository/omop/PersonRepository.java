package org.octri.omop_annotator.repository.omop;

import org.octri.omop_annotator.domain.omop.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "person")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
}
