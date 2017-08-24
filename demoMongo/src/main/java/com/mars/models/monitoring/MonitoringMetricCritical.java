package com.mars.models.monitoring;

public class MonitoringMetricCritical  extends MonitoringMetric{
	
	public MonitoringMetricCritical(String stdout) {
		super(new MonitoringMetricAttributes(NagiosStatusEnum.CRITICAL.intValue(), NagiosStatusEnum.CRITICAL  + " " + stdout));
	}
}
