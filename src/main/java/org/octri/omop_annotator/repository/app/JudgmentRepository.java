package org.octri.omop_annotator.repository.app;

import java.util.List;

import org.octri.omop_annotator.domain.app.Judgment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "judgment")
public interface JudgmentRepository extends PagingAndSortingRepository<Judgment, Long> {

    Judgment findByUserIdAndPoolEntryId(Long userId, Long poolEntryId);

    Long countByPoolEntryId(Long id);

    List<Judgment> findAllByUserIdAndPoolEntryPoolId(Long userId, Long poolId);

    List<Judgment> findAllByPoolEntryPoolId(Long poolId);
}
