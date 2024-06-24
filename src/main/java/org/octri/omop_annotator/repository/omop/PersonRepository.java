package org.octri.omop_annotator.repository.omop;

import org.octri.omop_annotator.domain.omop.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * NOTE: Avoid using Repositories directly. To ensure proper request logging and access control,
 * implement a Service class that wraps the repository and use that to manipulate domain entities
 * instead.
 */
@RepositoryRestResource(path = "person")
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
