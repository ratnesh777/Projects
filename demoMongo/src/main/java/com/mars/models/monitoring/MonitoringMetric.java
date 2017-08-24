package com.mars.models.monitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonitoringMetric {
	
	private MonitoringMetricAttributes metricAttributes;

	public MonitoringMetric() {
	}

	public MonitoringMetric(MonitoringMetricAttributes metricAttributes) {
		this.metricAttributes = metricAttributes;
	}

	@JsonProperty("value")
	public MonitoringMetricAttributes getMetricDto() {
		return metricAttributes;
	}

	public void setMetricDto(MonitoringMetricAttributes metricAttributes) {
		this.metricAttributes = metricAttributes;
	}
}
