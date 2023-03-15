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
import org.octri.omop_annotator.config.SearchIndexingConfig;
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
    private SearchIndexingConfig config;

    /**
     * Constructor
     *
     * @param entityManager
     *            - entity manager associated with the OMOP Database.
     */
    public Indexer(@Qualifier("omopEntityManagerFactory") EntityManager entityManager, SearchIndexingConfig config) {
        this.entityManager = entityManager;
        this.config = config;
    }

    /**
     * Index any data already contained in the database. Note that this drops the
     * existing index if present.
     *
     * See
     * https://docs.jboss.org/hibernate/search/6.1/reference/en-US/html_single/#mapper-orm-indexing-massindexer
     * for details and configuration options.
     *
     * @param personIds
     *            - if provided, indexing will only be created for visits
     *            associated with the given person ids.
     * @throws IndexException
     */
    @Transactional
    public void indexPersistedData(List<Integer> personIds) throws IndexException {
        List<List<Integer>> batches = ListUtils.partition(personIds, config.getBatchSize());
        log.info("Starting OMOP data mass indexing. Batch count: " + batches.size()
                + "; max batch size: " + config.getBatchSize());

        List<MassIndexer> indexers = new ArrayList<MassIndexer>();
        var purge = true;
        for (List<Integer> ids : batches) {
            indexers.add(createIndexer(ids, purge));
            purge = false;
        }

        runIndexers(indexers.iterator(), 1);
    }

    /**
     * Runs the first MassIndexer in the provided iterator, then recursively runs
     * the rest.
     * MassIndexers are run asynchronously.
     *
     * @param indexers
     * @param counter
     */
    private void runIndexers(Iterator<MassIndexer> indexers, Integer counter) {
        if (indexers.hasNext()) {
            log.info("Running indexer batch " + counter);

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
        } else {
            log.info("Indexing complete.");
        }
    }

    /**
     * Creates a MassIndexer that can be run.
     *
     * @param personIds
     *            - list of Person ids to index
     * @param purge
     *            - whether or not to purge the existing index.
     * @return
     */
    private MassIndexer createIndexer(List<Integer> personIds, Boolean purge) {
        Assert.notNull(personIds, "personIds required");
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
                .purgeAllOnStart(purge);
        return indexer;
    }
}
