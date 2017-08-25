package com.mars;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.mars.AppConfig;
import com.mars.WebMvcConfig;

@Configuration
//@Profile({ "dev" })
@Import({ AppConfig.class, WebMvcConfig.class })
public class TestAppConfig
{

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabaseCleaner(databaseCleaner());
        dataSourceInitializer.setDatabasePopulator(databasePopulator());
        return dataSourceInitializer;
    }

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
       // Resource script = new FileSystemResource("src/test/resources/populateDB.sql");
        Resource script = new FileSystemResource("C:\\ratnesh\\Projects\\portal\\src\\test\\resources\\populateDB.sql");
        resourceDatabasePopulator.addScript(script);
        return resourceDatabasePopulator;
    }

    private DatabasePopulator databaseCleaner() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        //Resource script = new FileSystemResource("src/test/resources/cleanUp.sql");
        Resource script = new FileSystemResource("C:\\ratnesh\\Projects\\portal\\src\\test\\resources\\cleanUp.sql");
        resourceDatabasePopulator.addScript(script);
        return resourceDatabasePopulator;
    }


}
