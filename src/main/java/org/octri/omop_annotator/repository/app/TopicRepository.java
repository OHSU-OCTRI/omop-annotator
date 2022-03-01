package org.octri.omop_annotator.repository.app;

import org.octri.omop_annotator.domain.app.Topic;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "topic")
public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {
}
