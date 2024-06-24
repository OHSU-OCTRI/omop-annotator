/**
 * Package defining repository access for OMOP domain objects. For each domain object, create an interface
 * extending CrudRepository or ListCrudRepository and annotated with @RepositoryRestResource(path = "...").
 *
 * NOTE: Avoid using Repositories directly in Controller classes. To ensure proper request logging and access control,
 * implement a Service class that wraps the repository and use that to manipulate OMOP domain entities instead.
 *
 * @see org.octri.omop_annotator.service.omop
 */
package org.octri.omop_annotator.repository.omop;
