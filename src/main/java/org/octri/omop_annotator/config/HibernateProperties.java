package org.octri.omop_annotator.config;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.AvailableSettings;

/**
 * Configuration properties for Hibernate.
 */
public class HibernateProperties {

	/**
	 * Default implicit naming strategy to use. Determines how Hibernate converts entity names and field names to
	 * logical names when names are not specified using annotations. Using this strategy, names are converted from
	 * camelCase to lower_snake_case.
	 */
	private static final String DEFAULT_IMPLICIT_NAMING_STRATEGY = "org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyHbmImpl";

	/**
	 * Default physical naming strategy to use. Determines how Hibernate converts logical names from the implicit naming
	 * strategy to table and column names. Using this strategy, names are converted from camelCase to lower_snake_case.
	 */
	private static final String DEFAULT_PHYSICAL_NAMING_STRATEGY = "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy";

	/**
	 * Fully-qualified class name of the Hibernate database dialect the entity manager should use. See the Hibernate
	 * documentation for valid values.
	 *
	 * @see https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#database-dialect
	 */
	private String dialect;

	/**
	 * Value of the hibernate.hbm2ddl.auto property to configure on the entity manager. See the Hibernate documentation
	 * for valid values. Defaults to "validate".
	 *
	 * @see https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#configurations-hbmddl
	 */
	private String ddlAuto = "validate";

	/**
	 * Whether Hibernate should log the SQL queries executed. Defaults to false.
	 */
	private Boolean showSql = false;

	/**
	 * Whether Hibernate should use the new ID generator mapping introduced in Hibernate 5. Defaults to false.
	 *
	 * @see https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#_identifier_options
	 */
	private Boolean useNewIdGeneratorMappings = false;

	/**
	 * Fully-qualified class name of the naming strategy Hibernate should use when converting entity names to logical
	 * names. Defaults to {@link DEFAULT_IMPLICIT_NAMING_STRATEGY}.
	 */
	private String implicitNamingStrategy = DEFAULT_IMPLICIT_NAMING_STRATEGY;

	/**
	 * Fully-qualified class name of the naming strategy Hibernate should use when converting logical names to physical
	 * table and column names. Defaults to {@link DEFAULT_PHYSICAL_NAMING_STRATEGY}.
	 *
	 * @see https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#_naming_strategies
	 */
	private String physicalNamingStrategy = DEFAULT_PHYSICAL_NAMING_STRATEGY;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getDdlAuto() {
		return ddlAuto;
	}

	public void setDdlAuto(String ddlAuto) {
		this.ddlAuto = ddlAuto;
	}

	public Boolean getShowSql() {
		return showSql;
	}

	public void setShowSql(Boolean showSql) {
		this.showSql = showSql;
	}

	public Boolean getUseNewIdGeneratorMappings() {
		return useNewIdGeneratorMappings;
	}

	public void setUseNewIdGeneratorMappings(Boolean useNewIdGeneratorMappings) {
		this.useNewIdGeneratorMappings = useNewIdGeneratorMappings;
	}

	public String getImplicitNamingStrategy() {
		return implicitNamingStrategy;
	}

	public void setImplicitNamingStrategy(String implicitNamingStrategy) {
		this.implicitNamingStrategy = implicitNamingStrategy;
	}

	public String getPhysicalNamingStrategy() {
		return physicalNamingStrategy;
	}

	public void setPhysicalNamingStrategy(String physicalNamingStrategy) {
		this.physicalNamingStrategy = physicalNamingStrategy;
	}

	public Map<String, Object> toPropertyMap() {
		Map<String, Object> propertyMap = new HashMap<>();
		propertyMap.put(AvailableSettings.DIALECT, getDialect());
		propertyMap.put(AvailableSettings.HBM2DDL_AUTO, getDdlAuto());
		propertyMap.put(AvailableSettings.SHOW_SQL, getShowSql());
		propertyMap.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, getImplicitNamingStrategy());
		propertyMap.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, getPhysicalNamingStrategy());
		return propertyMap;
	}

	@Override
	public String toString() {
		return "HibernateProperties [dialect=" + dialect + ", ddlAuto=" + ddlAuto + ", showSql=" + showSql
				+ ", useNewIdGeneratorMappings=" + useNewIdGeneratorMappings + ", implicitNamingStrategy="
				+ implicitNamingStrategy + ", physicalNamingStrategy=" + physicalNamingStrategy + "]";
	}

}
