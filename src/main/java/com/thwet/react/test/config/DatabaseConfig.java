package com.thwet.react.test.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DatabaseConfig {
	@Value("${jdbc.driver}")
	private String driverClassName;

	@Value("${jdbc.user}")
	private String user;

	@Value("${jdbc.password}")
	private String password;

	@Value("${jdbc.url}")
	private String url;

	@Value("${hibernate.dialect}")
	private String hibernateDialect;

	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;

	@Value("${hibernate.format_sql}")
	private String hibernateFormatSql;

	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2DdlAuto;

	@Value("${entitymanager.packagesToScan}")
	private String packagesToScan;

	private URI getDBUrl() {
		try {
			return new URI(System.getenv("DATABASE_URL"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Bean("clientDataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUsername(getDBUrl().getUserInfo().split(":")[0]);
		dataSource.setPassword(getDBUrl().getUserInfo().split(":")[1]);
		dataSource.setUrl(
				"jdbc:postgresql://" + getDBUrl().getHost() + ":" + getDBUrl().getPort() + getDBUrl().getPath());
		return dataSource;
	}

	@Bean("transactionManager")
	@Autowired
	public PlatformTransactionManager getTransactionManager(
			@Qualifier("test") EntityManagerFactory entityManagerFactory) throws NamingException {
		JpaTransactionManager jpaTransaction = new JpaTransactionManager();
		jpaTransaction.setEntityManagerFactory(entityManagerFactory);
		return jpaTransaction;
	}

	@Bean("test")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(getDataSource());
		emf.setPackagesToScan(packagesToScan);
		emf.setJpaVendorAdapter(getHibernateAdapter());
		emf.setPersistenceUnitName("test");
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", hibernateDialect);
		jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2DdlAuto);
		jpaProperties.put("hibernate.show_sql", hibernateShowSql);
		jpaProperties.put("hibernate.format_sql", hibernateFormatSql);
		emf.setJpaProperties(jpaProperties);
		return emf;
	}

	@Bean("hibernateAdapter")
	public JpaVendorAdapter getHibernateAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean("propertySourcesPlaceholderConfigurer")
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
