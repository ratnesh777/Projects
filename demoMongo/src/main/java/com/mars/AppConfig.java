package com.mars;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.client.RestTemplate;

import com.mars.mongodb.MongodbClusterListener;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;


@Configuration
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
@EnableScheduling
@EnableMongoRepositories(basePackages = "com.mars.mongo.repository")
public class AppConfig
{
  /*  @Value("${cloud.smtp.host}")
    private String smtpHost;

    @Value("${cloud.smtp.port}")
    private String smtpPort;*/

    @Value("${mongodb.database}")
    private String databaseName;

    @Value("${mongodb.host}")
    private String databaseHost;

    @Value("${mongodb.port}")
    private String databasePort;

    Logger logger = LogManager.getLogger();
    
    @Autowired
    private MongodbClusterListener mongodbClusterListener;

    @Bean(name = "mongoDbFactory")
    public MongoDbFactory mongoDbFactory() throws Exception
    {
        logger.info("Instantiating mongo database configuration");
        ServerAddress serverAddress = new ServerAddress(databaseHost, Integer.parseInt(databasePort.trim()));
        MongoClientOptions options = MongoClientOptions.builder()
                .addClusterListener(mongodbClusterListener)
                .build();
        return new SimpleMongoDbFactory(
                new MongoClient(serverAddress, options), databaseName);
       
    }
    

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() throws Exception
    {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }
/*
    @Bean(name = "mailSender")
    public JavaMailSender configureJavaMail() throws NamingException
    {
        logger.info("Configuring java mail server");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(Integer.valueOf(smtpPort));
        mailSender.setProtocol("smtp");
        return mailSender;
    }*/

    @Bean(name = "passwdResetMail")
    public SimpleMailMessage configurePasswordResetMail() throws NamingException
    {
        SimpleMailMessage passwordResetMail = new SimpleMailMessage();
        passwordResetMail.setFrom("cloudAdmin@mars.com");
        passwordResetMail.setSubject("Password Resets");
        return passwordResetMail;
    }

    @Bean(name = "freemarkerConfiguration")
    public FreeMarkerConfigurationFactoryBean configFreeMarker() throws NamingException
    {
        FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean = new FreeMarkerConfigurationFactoryBean();
        freeMarkerConfigurationFactoryBean.setTemplateLoaderPath("classpath:template/");
        return freeMarkerConfigurationFactoryBean;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

}
