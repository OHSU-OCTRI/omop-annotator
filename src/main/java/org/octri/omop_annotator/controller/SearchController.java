package org.octri.omop_annotator.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.octri.omop_annotator.domain.app.PoolEntry;
import org.octri.omop_annotator.repository.app.PoolEntryRepository;
import org.octri.omop_annotator.search.IndexException;
import org.octri.omop_annotator.search.Indexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin functionality for managing search indices.
 */
@RestController
@RequestMapping("admin/search")
public class SearchController {

    @Autowired
    private PoolEntryRepository poolEntryRepository;

    @Autowired
    private Indexer indexer;

    /**
     * Initialize the search index.
     *
     * @throws IndexException
     */
    @GetMapping("/init_index")
    public String initIndex() throws IndexException {
        List<Integer> ids = StreamSupport.stream(poolEntryRepository.findAll().spliterator(), false)
                .map(PoolEntry::getDocumentId)
                .distinct()
                .collect(Collectors.toList());

        indexer.indexPersistedData(ids);

        return "Indexing initiated";
    }
}
