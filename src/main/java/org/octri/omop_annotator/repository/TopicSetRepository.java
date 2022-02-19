package org.octri.omop_annotator.repository;

import org.octri.omop_annotator.domain.TopicSet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "topic_set")
public interface TopicSetRepository extends PagingAndSortingRepository<TopicSet, Long> {
}