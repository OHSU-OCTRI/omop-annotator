package org.octri.omop_annotator.repository.app;

import org.octri.omop_annotator.domain.app.TopicSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "topic_set")
public interface TopicSetRepository extends CrudRepository<TopicSet, Long> {
}
