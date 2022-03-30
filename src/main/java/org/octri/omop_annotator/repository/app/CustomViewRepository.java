package org.octri.omop_annotator.repository.app;

import java.util.List;

import org.octri.authentication.server.security.entity.User;
import org.octri.omop_annotator.view.PoolEntryJudgmentSummary;
import org.octri.omop_annotator.view.TopicJudgmentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This repository calls native sql queries to build custom views that span entities.
 * Note that the User type in the JpaRepository template is a dummy placeholder. The views can't be used here 
 * because they are not managed entities.
 * 
 * @author yateam
 *
 */
public interface CustomViewRepository extends JpaRepository<User, Long> {
	
	static final String topicJudgmentQuery = "select t.id as 'topicId', t.topic_number as 'topicNumber', t.narrative as 'narrative', count(distinct pe.id) as 'numEntries', count(j.id) as 'completedJudgments' from topic t"
			+ "	join topic_set ts on ts.id = t.topic_set"
			+ " join pool p on p.topic_set = ts.id and p.id = ?1"
			+ " left join pool_entry pe on pe.topic = t.id and pe.pool = p.id"
			+ " left join judgment j on j.pool_entry = pe.id and j.user = ?2"
			+ " group by t.id, t.topic_number, t.narrative;";
	
	static final String poolEntryJudgmentQuery = "select pe.id as 'poolEntryId', pe.document_id as 'documentId', pe.sort_order as 'sortOrder', j.id as 'judgmentId', al.display_label as 'annotation' from pool_entry pe"
			+ " left join judgment j on j.pool_entry = pe.id and j.user = ?3"
			+ " left join annotation_label al on al.id = j.annotation_label"
			+ " where pe.pool = ?1 and pe.topic = ?2";

	/**
	 * Return a summary of the topics for the given pool and count of judgments by the user
	 * 
	 * @param poolId the pool to get topics for
	 * @param userId the user to summarize judgments for
	 * @return
	 */
	@Query(value = topicJudgmentQuery, nativeQuery = true)
	List<TopicJudgmentSummary> summarizeTopicJudgments(Long poolId, Long userId);

	/**
	 * Return a summary of the pool entries for the given pool and topics and judgments by the user
	 * 
	 * @param poolId
	 * @param topicId
	 * @param userId
	 * @return
	 */
	@Query(value = poolEntryJudgmentQuery, nativeQuery = true)
	List<PoolEntryJudgmentSummary> summarizePoolEntryJudgments(Long poolId, Long topicId, Long userId);

}
