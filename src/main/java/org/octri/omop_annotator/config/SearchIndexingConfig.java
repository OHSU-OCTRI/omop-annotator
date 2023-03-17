package org.octri.omop_annotator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for populating the search index using the Indexer.
 */
@Configuration
@ConfigurationProperties(prefix = "search-indexing")
public class SearchIndexingConfig {

    // For performance reasons, the Indexer does not index the entire OMOP database. The API requires a list of
    // personIds to index. If the number of provided ids exceeds what a database allows in a selection list, this value
    // is used to partition the list of ids into batches that can be run.
    public Integer batchSize;

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

}
