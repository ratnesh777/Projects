package com.mars;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "com.mars.repository", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "entityTransactionManager")
public class AppConfig
{

    @Value("${dataSourceJndiName}")
    private String dataSourceJndiName;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.connection.driver_class}")
    private String driverClass;

    @Value("${hibernate.connection.url}")
    private String jdbcUrl;

    @Value("${db.username}")
    private String dbUser;

    @Value("${db.password}")
    private String dbPassword;

    @Value("${hibernate.show_sql}")
    private boolean showSql;

    @Value("${hibernate.jpa.persistenceUnitName}")
    private String persistenceUnitName;

    @Value("${hibernate.packagesToScan}")
    private String packagesToScan;
    
    @Value("${cloud.smtp.host}")
    private String smtpHost;
    
    @Value("${cloud.smtp.port}")
    private String smtpPort;

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean(name = "dataSource")
    public DataSource configureDataSource() throws NamingException
    {
        if (dataSourceJndiName != null && !dataSourceJndiName.isEmpty())
        {
            logger.info("Instantiating Spring DataSource using container managed datasource "
                    + dataSourceJndiName);
            JndiObjectFactoryBean jndiOFB = new JndiObjectFactoryBean();
            jndiOFB.setExposeAccessContext(true);
            jndiOFB.setJndiName(dataSourceJndiName);
            jndiOFB.afterPropertiesSet();
            return (DataSource) jndiOFB.getObject();
        }
        else
        {
            logger.info("Instantiating local Spring DataSource");
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverClass);
            dataSource.setUrl(jdbcUrl);
            if (dbUser != null && !dbUser.isEmpty())
            {
                dataSource.setUsername(dbUser);
                dataSource.setPassword(dbPassword);
            }
            return dataSource;
        }
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean configureEntityManagerFactory()
            throws NamingException
    {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(configureDataSource());

        HibernateJpaVendorAdapter hibernateJpa = new HibernateJpaVendorAdapter();
        hibernateJpa.setDatabasePlatform(this.dialect);
        hibernateJpa.setShowSql(this.showSql);

        lef.setJpaVendorAdapter(hibernateJpa);
        lef.setPersistenceUnitName(persistenceUnitName);
        // lef.setPersistenceProvider(HibernatePersistenceProvider.class);
        lef.setPackagesToScan(packagesToScan.split(","));
        Properties jpaProperties = new Properties();

        // Configures the used database dialect. This allows Hibernate to create SQL
        // that is optimized for the used database.
        jpaProperties.put("hibernate.dialect", dialect);

        // Specifies the action that is invoked to the database when the Hibernate
        // SessionFactory is created or closed.
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");

        // Configures the naming strategy that is used when Hibernate creates
        // new database objects and schema elements
        jpaProperties.put("hibernate.ejb.naming_strategy",
                "org.hibernate.cfg.ImprovedNamingStrategy");

        // If the value of this property is true, Hibernate writes all SQL
        // statements to the console.
        jpaProperties.put("hibernate.show_sql", "true");

        // If the value of this property is true, Hibernate will format the SQL
        // that is written to the console.

        lef.setJpaProperties(jpaProperties);

        return lef;
    }

    @Bean(name = "entityTransactionManager")
    public PlatformTransactionManager transactionManager() throws NamingException
    {
        JpaTransactionManager transManager = new JpaTransactionManager();
        transManager.setEntityManagerFactory(configureEntityManagerFactory().getObject());
        return transManager;
    }

    
    @Bean(name = "mailSender")
    public JavaMailSender configureJavaMail() throws NamingException
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(Integer.valueOf(smtpPort));
        mailSender.setProtocol("smtp");
        return mailSender;
    }
    
    @Bean(name = "passwdResetMail")
    public SimpleMailMessage configurePasswordResetMail() throws NamingException
    {
        SimpleMailMessage passwordResetMail = new SimpleMailMessage();
        passwordResetMail.setFrom("cloudAdmin@mars.com");
        passwordResetMail.setSubject("Password Resets");       
        return passwordResetMail;
    }
    
    @Bean(name = "freemarkerConfiguration")
    public FreeMarkerConfigurationFactoryBean configFreeMarker() throws NamingException{
    	FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean = new FreeMarkerConfigurationFactoryBean();
    	freeMarkerConfigurationFactoryBean.setTemplateLoaderPath("classpath:template/");
    	return freeMarkerConfigurationFactoryBean;
    }
  
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
