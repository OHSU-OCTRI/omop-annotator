package org.octri.omop_annotator.repository.app;

import java.util.List;

import org.octri.authentication.server.security.entity.User;
import org.octri.omop_annotator.view.PoolEntryJudgmentSummary;
import org.octri.omop_annotator.view.PoolSummary;
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

	static final String poolSummaryQuery = "select p.id as 'poolId', p.`name` as 'poolName', t.id as 'topicId', t.topic_number as 'topicNumber',"
			+ " t.narrative as 'topicNarrative', count(pe.id) as 'poolSize', j.username as 'judge', j.completed as 'completed'"
			+ " from pool p"
			+ " join pool_entry pe on pe.pool = p.id"
			+ " join topic t on pe.topic = t.id"
			+ " left join (select p.id as 'pool_id', t.id as 'topic_id', u.username, count(j.id) as 'completed' from judgment j"
			+ " join pool_entry pe on j.pool_entry = pe.id"
			+ " join pool p on p.id = pe.pool"
			+ " join topic t on pe.topic = t.id"
			+ " join user u on u.id = j.user"
			+ " group by p.id, t.id, u.id, u.username) j on j.pool_id = p.id and j.topic_id = t.id"
			+ " where p.id = ?1"
			+ " group by p.id, p.`name`, t.id, t.topic_number, t.narrative, j.username, j.completed";

	/**
	 * Return a summary of the topics for the given pool and count of judgments by the user
	 * 
	 * @param poolId
	 *            the pool to get topics for
	 * @param userId
	 *            the user to summarize judgments for
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

	/**
	 * Return a summary of the topics for the given pool and count of judgments by each user
	 * 
	 * @param poolId
	 *            the pool to summarize judgments for
	 * @return
	 */
	@Query(value = poolSummaryQuery, nativeQuery = true)
	List<PoolSummary> summarizePool(Long poolId);

}
