package org.octri.omop_annotator.config;

import org.hibernate.search.mapper.orm.mapping.HibernateOrmMappingConfigurationContext;
import org.hibernate.search.mapper.orm.mapping.HibernateOrmSearchMappingConfigurer;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.mapping.definition.programmatic.ProgrammaticMappingConfigurationContext;
import org.hibernate.search.mapper.pojo.mapping.definition.programmatic.TypeMappingStep;
import org.octri.omop_annotator.domain.omop.VisitOccurrence;

/**
 * Manually specifies which entities and fields are indexed. The alternative method is to provide annotations in the
 * classes.
 */
public class SearchMappingConfigurer implements HibernateOrmSearchMappingConfigurer {

    @Override
    public void configure(HibernateOrmMappingConfigurationContext context) {
        ProgrammaticMappingConfigurationContext mapping = context.programmaticMapping();
        TypeMappingStep visitMapping = mapping.type(VisitOccurrence.class);
        visitMapping.indexed().index("visit");
        visitMapping.property("visitSourceValue")
                .fullTextField();
        visitMapping.property("parent").indexedEmbedded().indexingDependency().reindexOnUpdate(ReindexOnUpdate.SHALLOW);
    }

}
