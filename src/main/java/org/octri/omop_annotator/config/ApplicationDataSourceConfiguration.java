package org.octri.omop_annotator.config;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration for the data source that stores application domain entities.
 */
@Configuration
@EnableJpaRepositories(basePackages = { "org.octri.omop_annotator.repository.app",
		"org.octri.authentication" }, entityManagerFactoryRef = "applicationEntityManagerFactory", transactionManagerRef = "applicationTransactionManager")
public class ApplicationDataSourceConfiguration {

	private static final Log log = LogFactory.getLog(ApplicationDataSourceConfiguration.class);

	private static final String[] APP_DOMAIN_PACKAGES = new String[] {
			"org.octri.omop_annotator.domain.app",
			"org.octri.authentication"
	};

	@Bean(name = "applicationDataSourceProperties")
	@ConfigurationProperties(prefix = "app.datasource")
	public DataSourceProperties applicationDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean(name = "applicationDataSource")
	public DataSource applicationDataSource() {
		log.info("Creating annotator application data source");
		DataSourceProperties dataSourceProps = applicationDataSourceProperties();
		return DataSourceBuilder
				.create()
				.driverClassName(dataSourceProps.getDriverClassName())
				.url(dataSourceProps.getUrl())
				.username(dataSourceProps.getUsername())
				.password(dataSourceProps.getPassword())
				.build();
	}

	@Bean
	@ConfigurationProperties(prefix = "app.hibernate")
	public HibernateProperties applicationHibernateProperties() {
		return new HibernateProperties();
	}

	@Primary
	@Bean(name = "applicationEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean applicationEntityManagerFactory() {
		log.info("Creating annotator application entity manager");
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(applicationDataSource());
		factory.setPackagesToScan(APP_DOMAIN_PACKAGES);

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		HibernateProperties hibernateProps = applicationHibernateProperties();
		log.debug(hibernateProps);
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setJpaPropertyMap(hibernateProps.toPropertyMap());

		return factory;
	}

	@Primary
	@Bean(name = "applicationTransactionManager")
	public PlatformTransactionManager applicationTransactionManager(
			final @Qualifier("applicationEntityManagerFactory") LocalContainerEntityManagerFactoryBean applicationEntityManagerFactory) {
		log.info("Creating annotator applicaton transaction manager");
		return new JpaTransactionManager(applicationEntityManagerFactory.getObject());
	}

}
