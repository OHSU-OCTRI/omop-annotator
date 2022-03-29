package org.octri.omop_annotator.config;

import java.time.LocalDate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration related to how OMOP data is used throughout the application.
 *
 */
@Configuration
@ConfigurationProperties(prefix = "omop.data")
public class OmopDataConfiguration {

	private LocalDate refreshDate;
	private String dateFormat;

	public LocalDate getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(String refreshDateStr) {
		if (refreshDateStr != null && !refreshDateStr.isEmpty()) {
			this.refreshDate = LocalDate.parse(refreshDateStr);
		}
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

}
