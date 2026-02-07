package com.ftk.pg.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(basePackages = "com.ftk.pg.repo", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class HibernateConfigPg {

	
	private final Environment env;

	private Properties setHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("getepay.database.Dialect"));
		properties.put("hibernate.hbm2ddl.auto", "none");
		properties.put("hibernate.show_sql", "false");
		return properties;
	}

	@Bean
	@Primary
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getProperty("getepay.database.driver"));
		hikariConfig.setJdbcUrl(env.getProperty("getepaydb.database.url"));
		hikariConfig.setUsername(env.getProperty("getepaydb.database.username"));
		hikariConfig.setPassword(env.getProperty("getepaydb.database.password"));
		hikariConfig.setMaximumPoolSize(Integer.parseInt(env.getProperty("getepay.database.maximumPoolSize")));
		hikariConfig.setPoolName("getepayDbPool");

		return new HikariDataSource(hikariConfig);
	}

	@Bean(name = "entityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("com.ftk.pg.modal");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(setHibernateProperties());
		return em;
	}

	@Bean(name = "transactionManager")
	@Primary
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
}
