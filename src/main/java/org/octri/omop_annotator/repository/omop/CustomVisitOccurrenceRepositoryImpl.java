package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

/**
 * Implementation of the full text search functionality for VisitOccurrences.
 * https://docs.spring.io/spring-data/jpa/docs/2.6.10/reference/html/#repositories.custom-implementations
 */
@Transactional
public class CustomVisitOccurrenceRepositoryImpl implements CustomVisitOccurrenceRepository {

        private static final Log log = LogFactory.getLog(CustomVisitOccurrenceRepositoryImpl.class);

        private EntityManager entityManager;

        /**
         * Constructor
         *
         * @param entityManager
         *                - entity manager for the OMOP database.
         */
        public CustomVisitOccurrenceRepositoryImpl(@Qualifier("omopEntityManagerFactory") EntityManager entityManager) {
                this.entityManager = entityManager;
        }

        /**
         * Perform a full text search on a visit and its related entities. Search terms
         * can be a user-provided query string.
         *
         * See:
         * https://docs.jboss.org/hibernate/stable/search/reference/en-US/html_single/#search-dsl-predicate-simple-query-string
         *
         * @param personId
         *                - searching is limited to visits for the given Person.
         * @param term
         *                - term on which to search.
         */
        @Override
        public List<VisitOccurrence> search(Integer personId, String term) {
                log.debug("Running full text search for personId: " + personId);
                SearchSession searchSession = Search.session(entityManager);
                var result = searchSession.search(VisitOccurrence.class)
                                .where(f -> f.bool()
                                                .must(f.match().field("person.id")
                                                                .matching(personId))
                                                // and one of the targeted fields must match the term.
                                                .must(f.simpleQueryString()
                                                                .fields("visitSourceValue",
                                                                                "careSite.careSiteName",
                                                                                "provider.providerName",
                                                                                "conditionOccurrences.condition.name",
                                                                                "observations.observation.name",
                                                                                "procedureOccurrences.procedure.name",
                                                                                "measurements.measurement.name",
                                                                                "notes.text", "drugExposures.drug.name")
                                                                .matching(term)));
                return result.fetchAllHits();
        }

        @Override
        public List<VisitOccurrence> findAllWithData(Integer personId) {
                log.debug("Searching for visits with data.");
                SearchSession searchSession = Search.session(entityManager);
                var result = searchSession.search(VisitOccurrence.class)
                                .where(f -> f.bool()
                                                .must(f.match().field("person.id")
                                                                .matching(personId))
                                                // and at least one of the related fields must have data.
                                                .must(f.bool()
                                                                .should(f.exists().field(
                                                                                "conditionOccurrences.condition.name"))
                                                                .should(f.exists()
                                                                                .field("observations.observation.name"))
                                                                .should(f.exists().field(
                                                                                "procedureOccurrences.procedure.name"))
                                                                .should(f.exists()
                                                                                .field("measurements.measurement.name"))
                                                                .should(f.exists().field("notes.text"))
                                                                .should(f.exists().field("drugExposures.drug.name"))));

                var hits = result.fetchAllHits();
                log.debug("Matched hits: " + hits.size());
                return hits;
        }

        @Override
        public List<VisitOccurrence> searchNotes(Integer personId, String term) {
                log.debug("Running note search for personId: " + personId);
                SearchSession searchSession = Search.session(entityManager);
                var result = searchSession.search(VisitOccurrence.class)
                                .where(f -> f.bool()
                                                .must(f.match().field("person.id")
                                                                .matching(personId))
                                                .must(f.simpleQueryString()
                                                                .fields("notes.text")
                                                                .matching(term)));
                return result.fetchAllHits();
        }
}
