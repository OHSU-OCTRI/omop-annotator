package org.octri.omop_annotator.repository.app;

import org.octri.omop_annotator.domain.app.OmopDisplayConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "omop_display_configuration")
public interface OmopDisplayConfigurationRepository extends CrudRepository<OmopDisplayConfiguration, Long> {

}