package org.octri.omop_annotator.repository.omop;

import org.octri.omop_annotator.domain.omop.Measurement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "measurement")
public interface MeasurementRepository extends PagingAndSortingRepository<Measurement, Long> {
}
