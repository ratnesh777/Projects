package com.mars.error;

import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@Data
public class ErrorResource
{
    private String code;
    private String message;
    private List<FieldErrorResource> fieldErrors = null;
    private List<ErrorContextResource> errorContext = null;

    public ErrorResource()
    {
    }

    public ErrorResource(String code)
    {
        this.code = code;
    }

    public ErrorResource(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
