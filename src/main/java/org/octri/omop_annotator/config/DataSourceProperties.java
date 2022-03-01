package org.octri.omop_annotator.config;

/**
 * JDBC data source properties.
 */
public class DataSourceProperties {

	/**
	 * JDBC URL to use when connecting to the database.
	 */
	private String url;

	/**
	 * Database username.
	 */
	private String username;

	/**
	 * Database password.
	 */
	private String password;

	/**
	 * Fully-qualified class name of the JDBC driver to use.
	 */
	private String driverClassName;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

}
