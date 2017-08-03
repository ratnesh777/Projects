package com.ipc;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.ipc.repository",entityManagerFactoryRef = "entityManagerFactory", 
transactionManagerRef = "entityTransactionManager")
public class AppConfig {
   
    @Autowired
    public AppConfig() {
     }
    
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
    
   

    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
   // @Primary
    public DataSource configureDataSource() throws NamingException {
        if (dataSourceJndiName != null && !dataSourceJndiName.isEmpty()) {
            logger.info("Instantiating Spring DataSource using container managed datasource " + dataSourceJndiName);
            JndiObjectFactoryBean jndiOFB = new JndiObjectFactoryBean();
            jndiOFB.setExposeAccessContext(true);
            jndiOFB.setJndiName(dataSourceJndiName);
            jndiOFB.afterPropertiesSet();
            return (DataSource) jndiOFB.getObject();
        } else {
            logger.info("Instantiating local Spring DataSource");
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(driverClass);
            dataSource.setUrl(jdbcUrl);
            if (dbUser != null && !dbUser.isEmpty()) {
                dataSource.setUsername(dbUser);
                dataSource.setPassword(dbPassword);
            }
            return dataSource;
        }
    }

   // @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean configureEntityManagerFactory() throws NamingException {
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

    @Bean(name = "entityTransactionManager")
  //  @Primary
    public PlatformTransactionManager transactionManager() throws NamingException {
        JpaTransactionManager transManager = new JpaTransactionManager();
        transManager.setEntityManagerFactory(configureEntityManagerFactory().getObject());
        return transManager;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
       /* JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName("localhost");
        jedisConFactory.setPort(6379);
        return jedisConFactory;*/
        
        return new JedisConnectionFactory();
    }
    
     
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    
    @Bean
    public RedisCacheManager cacheManager()
    {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.setLoadRemoteCachesOnStartup(true);
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }
    
}

