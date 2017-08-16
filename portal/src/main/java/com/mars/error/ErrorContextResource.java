package com.mars.error;

import lombok.Data;

/**
 * Copyright (c) 2017 IPC Systems, Inc.
 * Created by Viktor Bondarenko on 1/4/2017.
 */
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
