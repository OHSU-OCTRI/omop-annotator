package org.octri.omop_annotator.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.spi.TypeContributorList;
import org.octri.omop_annotator.hibernate.ToFloatTypeContributor;
import org.octri.omop_annotator.hibernate.ToTextTypeContributor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration for the data source that stores OMOP domain entities.
 */
@Configuration
@EnableJpaRepositories(basePackages = "org.octri.omop_annotator.repository.omop", entityManagerFactoryRef = "omopEntityManagerFactory", transactionManagerRef = "omopTransactionManager")
public class OmopDataSourceConfiguration {

	private static final Log log = LogFactory.getLog(OmopDataSourceConfiguration.class);

	public static final String OMOP_DOMAIN_PACKAGE = "org.octri.omop_annotator.domain.omop";

	@Bean(name = "omopDataSourceProperties")
	@ConfigurationProperties(prefix = "omop.datasource")
	public DataSourceProperties omopDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "omopDataSource")
	public DataSource omopDataSource() {
		log.info("Creating OMOP data source");
		DataSourceProperties dataSourceProps = omopDataSourceProperties();
		return DataSourceBuilder
				.create()
				.driverClassName(dataSourceProps.getDriverClassName())
				.url(dataSourceProps.getUrl())
				.username(dataSourceProps.getUsername())
				.password(dataSourceProps.getPassword())
				.build();
	}

	@Bean
	@ConfigurationProperties(prefix = "omop.hibernate")
	public HibernateProperties omopHibernateProperties() {
		return new HibernateProperties();
	}

	@Bean(name = "omopEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean omopEntityMangerFactory(EntityManagerFactoryBuilder builder) {
		log.info("Creating OMOP entity manager");
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(omopDataSource());
		factory.setPackagesToScan(OMOP_DOMAIN_PACKAGE);

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		HibernateProperties hibernateProps = omopHibernateProperties();
		log.debug(hibernateProps);
		factory.setJpaVendorAdapter(vendorAdapter);

		Map<String, Object> jpaProperties = hibernateProps.toPropertyMap();
		// Add TypeContributors needed to resolve dialect typing conflicts for OMOP
		jpaProperties.put(EntityManagerFactoryBuilderImpl.TYPE_CONTRIBUTORS, new TypeContributorList() {

			@Override
			public List<TypeContributor> getTypeContributors() {
				return Arrays.asList(new ToFloatTypeContributor(omopHibernateProperties().getDialect()),
						new ToTextTypeContributor(omopHibernateProperties().getDialect()));
			}
		});

		factory.setJpaPropertyMap(jpaProperties);

		return factory;
	}

	@Bean(name = "omopTransactionManager")
	public PlatformTransactionManager omopTransactionManager(
			final @Qualifier("omopEntityManagerFactory") LocalContainerEntityManagerFactoryBean omopEntityManagerFactory) {
		log.info("Creating OMOP transaction manager");
		return new JpaTransactionManager(omopEntityManagerFactory.getObject());
	}

}
