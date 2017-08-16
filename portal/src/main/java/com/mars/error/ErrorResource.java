package com.mars.error;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;


/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=Include.NON_NULL)
@Data
public class ErrorResource {
    private String code;
    private String message;
    private List<FieldErrorResource> fieldErrors=null;
    private List<ErrorContextResource> errorContext =null;

    public ErrorResource() { }

    public ErrorResource(String code) {
        this.code = code;
    }

    public ErrorResource(String code, String message) {
        this.code = code;
        this.message = message;
    }
}