package org.octri.omop_annotator.repository.app;

import java.util.List;

import org.octri.omop_annotator.domain.app.Pin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pin")
public interface PinRepository extends PagingAndSortingRepository<Pin, Long> {

    List<Pin> findByUserIdAndPoolEntryId(Long userId, Long poolEntryId);

}