package org.octri.omop_annotator.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Typically the properties in this config would be prefixed with 'spring.jpa.properties.hibernate.' and passed by
 * Spring Boot directly to Hibernate, but we are using multiple data sources so we need to pass them in manually. See:
 * OmopDataSourceConfiguration for details.
 */
@Configuration
@ConfigurationProperties(prefix = "search")
public class SearchConfig {

    /**
     * Settings currently supported by this class.
     */
    public class AvailableSettings {

        private static final String BACKEND_TYPE = "hibernate.search.backend.type";
        private static final String BACKEND_DIRECTORY_ROOT = "hibernate.search.backend.directory.root";
    }

    private String backendType;
    private String backendDirectoryRoot;

    public String getBackendType() {
        return backendType;
    }

    public void setBackendType(String backendType) {
        this.backendType = backendType;
    }

    public String getBackendDirectoryRoot() {
        return backendDirectoryRoot;
    }

    public void setBackendDirectoryRoot(String backendDirectoryRoot) {
        this.backendDirectoryRoot = backendDirectoryRoot;
    }

    public Map<String, Object> toPropertyMap() {
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put(AvailableSettings.BACKEND_TYPE, getBackendType());
        propertyMap.put(AvailableSettings.BACKEND_DIRECTORY_ROOT, getBackendDirectoryRoot());
        return propertyMap;
    }
}
