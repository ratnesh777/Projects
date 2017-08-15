package com.mars;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.github.fakemongo.Fongo;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringMongoConfigTest
{

    @Value("${mongodb.database}")
    private String databaseName;

    @Value("${mongodb.host}")
    private String databaseHost;

    @Value("${mongodb.port}")
    private String databasePort;

    @Bean(name = "mongoDbFactory")
    public MongoDbFactory mongoDbFactory() throws Exception
    {
        return new SimpleMongoDbFactory(new Fongo(databaseName).getMongo(), databaseName);
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() throws Exception
    {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

    @Bean
    public DBLoader dbLoader()
    {
        return new DBLoader();
    }

}
