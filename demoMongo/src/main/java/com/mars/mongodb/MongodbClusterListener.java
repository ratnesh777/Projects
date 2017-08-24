package com.mars.mongodb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mars.service.MonitoringService;
import com.mongodb.connection.ServerConnectionState;
import com.mongodb.connection.ServerDescription;
import com.mongodb.event.ClusterClosedEvent;
import com.mongodb.event.ClusterDescriptionChangedEvent;
import com.mongodb.event.ClusterListener;
import com.mongodb.event.ClusterOpeningEvent;

@Component
public class MongodbClusterListener implements ClusterListener{

	Logger logger = LogManager.getLogger();
	
	boolean mongoDBConnectionStatus = false;
	
	public boolean getMongoDBConnectionStatus(){
		return mongoDBConnectionStatus;
	}
	
	@Override
	public void clusterClosed(ClusterClosedEvent arg0) {
		logger.info("MongoDB connection status is closed." );
		mongoDBConnectionStatus = false;
	}
	
	@Override
	public void clusterDescriptionChanged(ClusterDescriptionChangedEvent clusterDescriptionChangedEvent) {
		boolean isConnected = false;
		for (ServerDescription serverDescription : clusterDescriptionChangedEvent.getNewDescription().getServerDescriptions()) {
			if(serverDescription.getState().equals(ServerConnectionState.CONNECTED)){
				isConnected = true;
			}
		}
		
		if(mongoDBConnectionStatus != isConnected){
			logger.info("MongoDB connection status changed. Previously connection was " 
					+ (mongoDBConnectionStatus ? "CONNECTED": "NOT CONNECTED") 
					+ " now connection is " + (isConnected ? "CONNECTED": "NOT CONNECTED"));
			mongoDBConnectionStatus = isConnected;
		}
		
	}
	
	@Override
	public void clusterOpening(ClusterOpeningEvent arg0) {
		logger.info("MongoDB connection status is opened." );
		mongoDBConnectionStatus = true;
	}
}
