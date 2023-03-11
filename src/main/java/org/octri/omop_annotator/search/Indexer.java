package org.octri.omop_annotator.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Component responsible for indexing existing database data.
 */
@Transactional
@Component
public class Indexer {

    private static final Log log = LogFactory.getLog(Indexer.class);
    private EntityManager entityManager;

    // Consider using configuration for this, since it is dictated by the underlying database.
    private static final Integer MAX_BATCH_SIZE = 999;
    private static final Integer BATCH_SIZE = 500;

    /**
     * Constructor
     *
     * @param entityManager
     *            - entity manager associated with the OMOP Database.
     */
    public Indexer(@Qualifier("omopEntityManagerFactory") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Index any data already contained in the database. Note that this drops the existing index if present.
     *
     * See https://docs.jboss.org/hibernate/search/6.1/reference/en-US/html_single/#mapper-orm-indexing-massindexer
     * for details and configuration options.
     *
     * @param personIds
     *            - if provided, indexing will only be created for visits associated with the given person ids.
     * @throws IndexException
     */
    @Transactional
    public void indexPersistedData(List<Integer> personIds) throws IndexException {

        log.info("Starting OMOP data mass indexing.");
        List<List<Integer>> subSets = ListUtils.partition(personIds, BATCH_SIZE);
        List<MassIndexer> indexers = new ArrayList<MassIndexer>();
        var purge = true;
        for (List<Integer> ids : subSets) {
            indexers.add(createIndexer(ids, purge));
            purge = false;
        }

        runIndexers(indexers.iterator(), 1);
    }

    private void runIndexers(Iterator<MassIndexer> indexers, Integer counter) {
        if (indexers.hasNext()) {
            indexers.next()
                    .start()
                    .thenRun(() -> {
                        log.info("Indexer " + counter + " completed!");
                        runIndexers(indexers, counter + 1);
                    })
                    .exceptionally(throwable -> {
                        log.error("Mass indexing failed!", throwable);
                        return null;
                    });
        }
    }

    private MassIndexer createIndexer(List<Integer> personIds, Boolean purge) {
        Assert.notNull(personIds, "personIds required");
        Assert.isTrue(personIds.size() < MAX_BATCH_SIZE, "MAX_BATCH_SIZE exceeded");
        SearchSession searchSession = Search.session(entityManager);

        MassIndexer indexer = searchSession.massIndexer();

        String cond = "e.person.id in (" + StringUtils.join(personIds, ",") + ")";
        indexer.type(VisitOccurrence.class).reindexOnly(cond);

        // Consider using configuration for tuning parameters
        // https://docs.jboss.org/hibernate/search/6.1/reference/en-US/html_single/#mapper-orm-indexing-massindexer-parameters
        indexer
                .idFetchSize(150)
                .batchSizeToLoadObjects(25)
                .threadsToLoadObjects(4)
                .purgeAllOnStart(false);
        return indexer;
    }
}
