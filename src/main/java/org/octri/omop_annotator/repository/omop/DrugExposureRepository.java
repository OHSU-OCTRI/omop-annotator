package org.octri.omop_annotator.repository.omop;

import java.util.List;

import org.octri.omop_annotator.domain.omop.DrugExposure;
import org.octri.omop_annotator.view.DrugExposureRow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "drug_exposure")
public interface DrugExposureRepository extends PagingAndSortingRepository<DrugExposure, Integer> {

    static final String query = "select de.id as id, de.person.id as personId, drug.name as drug,"
            + " drugType.name as drugType, de.drugStart as drugStart, de.drugEnd as drugEnd, "
            + " de.stopReason as stopReason, de.quantity as quantity, "
            + " visitOccurrence.id as visitOccurrence"
            + " from DrugExposure de"
            + " left join de.drug drug"
            + " left join de.drugType drugType"
            + " left join de.visitOccurrence visitOccurrence";

    @Query(query + " where de.person.id = ?1")
    List<DrugExposureRow> findByPersonId(Integer personId);

    @Query(query + " where visitOccurrence.id = ?1")
    List<DrugExposureRow> findByVisitOccurrenceId(Integer visitOccurrenceId);

}
