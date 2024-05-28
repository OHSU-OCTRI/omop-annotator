package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.DrugExposure;
import org.octri.omop_annotator.view.DrugExposureRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * NOTE: Avoid using Repositories directly. To ensure proper request logging and access control,
 * implement a Service class that wraps the repository and use that to manipulate domain entities
 * instead.
 */
@RepositoryRestResource(path = "drug_exposure")
public interface DrugExposureRepository extends ListCrudRepository<DrugExposure, Integer> {

    static final String query = "select de.id as id, de.person.id as personId, drug.name as drug,"
            + " drugType.name as drugType, de.drugStart as drugStart, de.drugEnd as drugEnd,"
            + " de.stopReason as stopReason, de.refills as refills, de.quantity as quantity,"
            + " de.daysSupply as daysSupply, drugSource.name as drugSource, de.drugSourceValue as drugSourceValue,"
            + " de.routeSourceValue as routeSourceValue, de.doseUnitSourceValue as doseUnitSourceValue,"
            + " visitOccurrence.id as visitOccurrence"
            + " from DrugExposure de"
            + " left join de.drug drug"
            + " left join de.drugType drugType"
            + " left join de.drugSource drugSource"
            + " left join de.visitOccurrence visitOccurrence";

    @Query(query + " where de.person.id = ?1")
    List<DrugExposureRow> findAllByPersonId(Integer personId);

    @Query(query + " where visitOccurrence.id = ?1")
    List<DrugExposureRow> findAllByVisitOccurrenceId(Integer visitOccurrenceId);

}
