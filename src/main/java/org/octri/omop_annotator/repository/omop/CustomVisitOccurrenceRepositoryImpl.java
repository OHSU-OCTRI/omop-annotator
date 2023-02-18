package org.octri.omop_annotator.repository.omop;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.octri.omop_annotator.domain.omop.VisitOccurrence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

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
     *            - entity manager for the OMOP database.
     */
    public CustomVisitOccurrenceRepositoryImpl(@Qualifier("omopEntityManagerFactory") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Perform a full text search on a visit and its related entities.
     *
     * @param personId
     *            - searching is limited to visits for the given Person.
     * @param term
     *            - term on which to search.
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
                        .must(f.match()
                                .fields("visitSourceValue", "careSite.careSiteName", "provider.providerName",
                                        "conditionOccurrences.condition.name", "observations.observation.name",
                                        "procedureOccurrences.procedure.name", "measurements.measurement.name",
                                        "notes.text", "drugExposures.drug.name")
                                .matching(term)));
        return result.fetchAllHits();
    }

}
