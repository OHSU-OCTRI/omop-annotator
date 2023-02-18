package org.octri.omop_annotator.search;

/**
 * Custom exception for issues with performing search indexing operations.
 */
public class IndexException extends Exception {

    public IndexException(String message) {
        super(message);
    }

    public IndexException(String message, Throwable cause) {
        super(message, cause);
    }
}
