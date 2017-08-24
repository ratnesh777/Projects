package com.mars.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mars.mongodb.MongodbClusterListener;



@Service
public class MonitoringServiceImpl extends AbstractServiceImpl implements MonitoringService{
	Logger logger = LogManager.getLogger();

    
    @Autowired
    private MongodbClusterListener mongodbClusterListener;
    
    @Override
    public boolean getMongoDBConnectionStatus() {
    	return mongodbClusterListener.getMongoDBConnectionStatus();
    }
    


}
