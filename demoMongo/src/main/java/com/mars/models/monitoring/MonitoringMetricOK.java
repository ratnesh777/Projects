package com.mars.models.monitoring;

public class MonitoringMetricOK  extends MonitoringMetric{
	
	public MonitoringMetricOK(String stdout) {
		super(new MonitoringMetricAttributes(NagiosStatusEnum.OK.intValue(), NagiosStatusEnum.OK  + " " + stdout));
	}
}
