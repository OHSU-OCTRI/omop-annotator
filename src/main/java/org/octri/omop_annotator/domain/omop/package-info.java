/**
 * Package for defining OMOP domain entities. Domain objects should have the @Entity annotation.
 * 
 * This contains a subset of the OMOP 5.3 domain. The following tables have been skipped, because they seem to provide little benefit in the RDW instance:
 * 
 * OBSERVATION_PERIOD: No relationship to anything but patient
 * VISIT_DETAIL: No information above and beyond what VISIT_OCCURRENCE provides and references to it seem to always be null
 * DEVICE_EXPOSURE: Empty
 * 
 * These tables still need evaluation and possible inclusion:
 * 
 * DEATH
 * NOTE_NLP
 * SPECIMEN
 * FACT_RELATIONSHIP
 * LOCATION
 * PAYER_PLAN_PERIOD
 * COST
 * DRUG_ERA
 * CONDITION_ERA
 * METADATA
 * CDM_SOURCE
 * VOCABULARY
 * DOMAIN
 * CONCEPT_CLASS
 * CONCEPT_RELATIONSHIP
 * RELATIONSHIP
 * CONCEPT_SYNONYM
 * SOURCE_TO_CONCEPT_MAP
 * DRUG_STRENGTH
 * COHORT_DEFINITION
 * ATTRIBUTE_DEFINITION
 * 
 * For complete details on available fields and tables, this is a handy resource:
 * 
 * https://github.com/OHDSI/CommonDataModel/tree/master/inst/csv
 */
package org.octri.omop_annotator.domain.omop;