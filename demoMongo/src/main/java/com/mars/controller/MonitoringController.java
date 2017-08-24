package com.mars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mars.models.monitoring.MonitoringMetric;
import com.mars.models.monitoring.MonitoringMetricCritical;
import com.mars.models.monitoring.MonitoringMetricOK;
import com.mars.service.MonitoringService;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@RestController
@RequestMapping(value = APIUtilConstant.MONITORING_API_END_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = APIUtilConstant.SWAGGER_MONITORING_API)
public class MonitoringController
{

    @Autowired
    private MonitoringService monitoringService;

    @ApiOperation(value = "Verify that the Cloud Portal server is running", response = MonitoringMetric.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = MonitoringMetric.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/heartbeat")
    public MonitoringMetric heartbeat(){
    	return new MonitoringMetricOK("Cloud Portal server is running and REST API are accessible.");
    }

    @ApiOperation(value = "Verify that the connection between Cloud Portal and MongoDB server is correct.", response = MonitoringMetric.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = MonitoringMetric.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/mongoDBConnectionStatus")
    public MonitoringMetric mongoDBConnectionStatus(){
    	MonitoringMetric mongoDBConnectionMetric = null;
    	if(monitoringService.getMongoDBConnectionStatus()){
    		mongoDBConnectionMetric = new MonitoringMetricOK("Connection well established with MongoDB server.");
    	}else{
    		mongoDBConnectionMetric = new MonitoringMetricCritical("Connection not established with MongoDB server.");    		
    	}
    	return mongoDBConnectionMetric;
    }

}


