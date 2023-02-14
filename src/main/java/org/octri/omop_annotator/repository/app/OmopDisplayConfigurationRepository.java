package org.octri.omop_annotator.repository.app;

import java.util.List;

import org.octri.omop_annotator.domain.app.OmopDisplayConfiguration;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "omop_display_configuration")
public interface OmopDisplayConfigurationRepository extends PagingAndSortingRepository<OmopDisplayConfiguration, Long> {

    List<OmopDisplayConfiguration> findAllByEntityName(String entityName);
}