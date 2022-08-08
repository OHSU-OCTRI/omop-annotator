package org.octri.omop_annotator.hibernate;

import java.util.Locale;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Custom physical naming strategy based on {@link CamelCaseToUnderscoresNamingStrategy} that converts table names to
 * UPPER_SNAKE_CASE, as used in the OMOP CDM DDL files.
 */
public class UpperSnakeCaseTablesPhysicalNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {

	@Override
	public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
		Identifier identifier = super.toPhysicalTableName(logicalName, jdbcEnvironment);
		String uppercaseName = identifier.getText().toUpperCase(Locale.ROOT);
		return Identifier.toIdentifier(uppercaseName, identifier.isQuoted());
	}

}
