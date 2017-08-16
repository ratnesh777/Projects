package com.mars.error;

import lombok.Data;

@Data
public class ErrorContextResource {
    private String key;
    private String value;

    public ErrorContextResource() {
    }

    public ErrorContextResource(String key, String value){
        this.key = key;
        this.value = value;
    }
}
