package org.octri.omop_annotator.repository.omop;

import org.octri.omop_annotator.domain.omop.DrugExposure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "drug_exposure")
public interface DrugExposureRepository extends PagingAndSortingRepository<DrugExposure, Long> {
}
