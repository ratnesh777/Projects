package com.mars.models.monitoring;

public enum NagiosStatusEnum {
	OK(0),
	WARNING(1),
	CRITICAL(2),
	UNKNOWN(3);

	private int intValue;
	
	public int intValue(){
        return intValue;
    }

    private NagiosStatusEnum(int intValue){
        this.intValue = intValue;
    }

}

