package com.ipc;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



/*@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.ipc.userpoc.model", 
						entityManagerFactoryRef = "poctestEntityManager",
						//transactionManagerRef = "entityTransactionManager")
						transactionManagerRef = "poctestTransactionManager")*/
public class DBTestConfig {
   
   /* @Autowired
    public DBTestConfig() {
     }
    
    @Value("${dataSourceJndiName}")
    private String dataSourceJndiName;
    
    @Value("${hibernate.dialect}")
    private String dialect;
    
    @Value("${hibernate.connection.driver_class}")
    private String driverClass;
    
 
   @Value("${hibernate.show_sql}")
    private boolean showSql;

    @Value("${hibernate.jpa.persistenceUnitNameTest}")
    private String persistenceUnitName;
   
   @Value("${hibernate.packagesToScantest}")
    private String packagesToScan;
    
   //TODO newly added
   @Value("${hibernate.connectionpoctest.url}")
   private String jdbcUrlpoctest;

   @Value("${dbpoctest.username}")
   private String dbUserpoctest;
   
   @Value("${dbpoctest.password}")
   private String dbPasswordpoctest;
   

    private static Logger logger = LoggerFactory.getLogger(DBTestConfig.class);

   
    @Bean
    public DataSource configureDataSourcepoctest() throws NamingException {
        if (dataSourceJndiName != null && !dataSourceJndiName.isEmpty()) {
            logger.info("Instantiating Spring DataSource using container managed datasource " + dataSourceJndiName);
            JndiObjectFactoryBean jndiOFB = new JndiObjectFactoryBean();
            jndiOFB.setExposeAccessContext(true);
            jndiOFB.setJndiName(dataSourceJndiName);
            jndiOFB.afterPropertiesSet();
            return (DataSource) jndiOFB.getObject();
        } else {
            logger.info("Instantiating local Spring DataSource");
            DriverManagerDataSource dataSourcepoctest = new DriverManagerDataSource();
            dataSourcepoctest.setDriverClassName(driverClass);
            dataSourcepoctest.setUrl(jdbcUrlpoctest);
            if (dbUserpoctest != null && !dbUserpoctest.isEmpty()) {
                dataSourcepoctest.setUsername(dbUserpoctest);
                dataSourcepoctest.setPassword(dbPasswordpoctest);
            }
            return dataSourcepoctest;
        }
    }

       
    @Bean(name = "poctestEntityManager")
    public LocalContainerEntityManagerFactoryBean configureEntityManagerFactorypoctest() throws NamingException {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(configureDataSourcepoctest());

        HibernateJpaVendorAdapter hibernateJpa = new HibernateJpaVendorAdapter();
        hibernateJpa.setDatabasePlatform(this.dialect);
        hibernateJpa.setShowSql(this.showSql);

        lef.setJpaVendorAdapter(hibernateJpa);
        //TODO lef.setPersistenceUnitName(persistenceUnitName);
        
        lef.setPersistenceUnitName(persistenceUnitName);
        lef.setPackagesToScan(packagesToScan.split(","));
        Properties jpaProperties = new Properties();
        
      //Configures the used database dialect. This allows Hibernate to create SQL
        //that is optimized for the used database.
        jpaProperties.put("hibernate.dialect", dialect);
 
        //Specifies the action that is invoked to the database when the Hibernate
        //SessionFactory is created or closed.
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
 
        //Configures the naming strategy that is used when Hibernate creates
        //new database objects and schema elements
        jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
 
        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        jpaProperties.put("hibernate.show_sql", "true");
 
        //If the value of this property is true, Hibernate will format the SQL
        //that is written to the console.
     
        lef.setJpaProperties(jpaProperties);
        
      
        return lef;
    }

     @Bean(name = "poctestTransactionManager")
    public PlatformTransactionManager transactionManager() throws NamingException {
        JpaTransactionManager transManager = new JpaTransactionManager();
        transManager.setEntityManagerFactory(configureEntityManagerFactorypoctest().getObject());
        return transManager;
    }
    */
  
}

