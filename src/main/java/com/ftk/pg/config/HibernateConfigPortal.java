package com.ftk.pg.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = "com.ftk.pg.pi.repo", entityManagerFactoryRef = "getepayPortalEntityManager", transactionManagerRef = "getepayPortalTransactionManager")
public class HibernateConfigPortal {

	// private Logger log = LogManager.getLogger(GetepayPortalConfiguration2.class);

 
   private	final Environment env;

	public Properties setHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("getepayportal.database.Dialect"));

//		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
//		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.hbm2ddl.auto", "none");
		properties.put("hibernate.show_sql", "false");

//		properties.put("hibernate.connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
//		properties.putAll(Map.of("hibernate.c3p0.min_size", "1", "hibernate.c3p0.max_size", "5",
//				"hibernate.c3p0.acquire_increment", "2", "hibernate.c3p0.max_statements", "5", "hibernate.c3p0.timeout",
//				"1800"));

//		properties.put("hibernate.c3p0.min_size", "1");
//		properties.put("hibernate.c3p0.max_size", "5");
//		properties.put("hibernate.c3p0.acquire_increment", "2");
//		properties.put("hibernate.c3p0.max_statements", "5");
//		properties.put("hibernate.c3p0.timeout", "1800");
//		properties.put("hibernate.connection.driver_class", env.getProperty("getepay.database.driver"));

		return properties;
	}

//	@Bean
//	public DataSource getpayPortalDataSource() throws PropertyVetoException {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(env.getProperty("getepay.database.driver"));
//		dataSource.setUrl(env.getProperty("getepayportal.database.url"));
//		dataSource.setUsername(env.getProperty("getepayportal.database.username"));
//
////		ComboPooledDataSource dataSource = new ComboPooledDataSource();
////		dataSource.setDriverClass(env.getProperty("getepay.database.driver"));
////		dataSource.setJdbcUrl(env.getProperty("getepayportal.database.url"));
////		dataSource.setUser(env.getProperty("getepayportal.database.username"));
//
//		
////		dataSource.setConnectionProperties(setConnectionPoolProperties());
////		dataSource.setMaxPoolSize(5);
////		dataSource.setMaxIdleTime(2000);
////		dataSource.setProperties(setHibernateProperties());
//		dataSource.setPassword(env.getProperty("getepayportal.database.password"));
//		return dataSource;
//	}

	@Bean
	public DataSource getpayPortalDataSource() throws PropertyVetoException {

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getProperty("getepay.database.driver"));
		hikariConfig.setJdbcUrl(env.getProperty("getepayportal.database.url"));
		hikariConfig.setUsername(env.getProperty("getepayportal.database.username"));
		hikariConfig.setPassword(env.getProperty("getepayportal.database.password"));
		hikariConfig.setMaximumPoolSize(Integer.valueOf(env.getProperty("getepay.database.maximumPoolSize")));
		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//		hikariConfig.addDataSourceProperty("maximumPoolSize", "11");
		hikariConfig.addDataSourceProperty("poolName", "getepayPortalPool");

		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		// log.info("Hikari pool size Portal===> "+hikariConfig.getMaximumPoolSize());
		return hikariDataSource;

	}

	@Bean
	public LocalContainerEntityManagerFactoryBean getepayPortalEntityManager() throws PropertyVetoException {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(getpayPortalDataSource());
		em.setPackagesToScan(new String[] { "com.ftk.pg.pi.modal" });
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(setHibernateProperties());
		return em;
	}

	@Bean
	public PlatformTransactionManager getepayPortalTransactionManager() throws PropertyVetoException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(getepayPortalEntityManager().getObject());
		return transactionManager;
	}
}
