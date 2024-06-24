package org.octri.omop_annotator.repository.app;

import java.util.List;

import org.octri.omop_annotator.domain.app.Topic;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "topic")
public interface TopicRepository extends ListCrudRepository<Topic, Long> {

	List<Topic> findByTopicSetId(Long topicSetId);
}
