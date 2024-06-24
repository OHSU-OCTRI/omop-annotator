package org.octri.omop_annotator.repository.app;

import java.util.List;

import org.octri.omop_annotator.domain.app.PoolEntry;
import org.octri.omop_annotator.view.MergeValidationSummary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import jakarta.transaction.Transactional;

@RepositoryRestResource(path = "pool_entry")
public interface PoolEntryRepository extends CrudRepository<PoolEntry, Long> {

	static final String mergeValidationQuery = "select topic_number as 'topicNumber' from pool_entry pe"
			+ " join topic t on pe.topic = t.id"
			+ " where pool in (?1,?2) group by topic_number having count(distinct pe.pool) > 1;";

	static final String mergePoolsQuery = "update pool_entry set pool=?2 where pool=?1";

	Long countByPoolId(Long id);

	Long countByPoolIdAndTopicId(Long poolId, Long topicId);

	/**
	 * Return results of checking for duplicate topics in two pools
	 * 
	 * @param poolId1
	 *            the first pool id
	 * @param poolId2
	 *            the second pool id
	 * @return
	 */
	@Query(value = mergeValidationQuery, nativeQuery = true)
	List<MergeValidationSummary> validatePoolMerge(Long poolId1, Long poolId2);

	@Transactional
	@Modifying
	@Query(value = mergePoolsQuery, nativeQuery = true)
	void mergePools(Long mergePoolId, Long destinationPoolId);

}
