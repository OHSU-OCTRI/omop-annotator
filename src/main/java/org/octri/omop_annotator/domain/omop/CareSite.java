package org.octri.omop_annotator.domain.omop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * OMOP 5.3 Definition of a Care Site
 * 
 * The following columns have been excluded:
 * 
 * PLACE_OF_SERVICE_CONCEPT_ID
 * LOCATION_ID
 * CARE_SITE_SOURCE_VALUE
 * PLACE_OF_SERVICE_SOURCE_VALUE
 */
@Entity
public class CareSite {

	@Column(name = "care_site_id")
	@Id
	private Integer id;

	@Column(name = "care_site_name")
	private String careSiteName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCareSiteName() {
		return careSiteName;
	}

	public void setCareSiteName(String careSiteName) {
		this.careSiteName = careSiteName;
	}

	@Override
	public String toString() {
		return "CareSite [id=" + id + ", careSiteName=" + careSiteName + "]";
	}

}
