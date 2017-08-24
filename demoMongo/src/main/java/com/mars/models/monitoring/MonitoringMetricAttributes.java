package com.mars.models.monitoring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class MonitoringMetricAttributes {
	
	private int returnCode;
	private String stdout;
	private String longServiceOutput;
	private String perfData;
	
	public MonitoringMetricAttributes() {
	}

	public MonitoringMetricAttributes(int returnCode, String stdout) {
		this.returnCode = returnCode;
		this.stdout = stdout;
	}

	@JsonProperty("returncode")
	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	@JsonProperty("stdout")
	public String getStdout() {
		return stdout;
	}

	public void setStdout(String stdout) {
		this.stdout = stdout;
	}

	@JsonProperty("longServiceOutput")
	public String getLongServiceOutput() {
		return longServiceOutput;
	}

	public void setLongServiceOutput(String longServiceOutput) {
		this.longServiceOutput = longServiceOutput;
	}

	@JsonProperty("perfData")
	public String getPerfData() {
		return perfData;
	}

	public void setPerfData(String perfData) {
		this.perfData = perfData;
	}
}
