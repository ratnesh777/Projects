package com.mars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class PortalRequestDataTypeException extends HttpMessageNotReadableException
{

    private Errors errors;

    public PortalRequestDataTypeException(String message, Errors errors)
    {
        super(message);
        this.errors = errors;
    }

    public PortalRequestDataTypeException(String message)
    {
        super(message);
    }

    public Errors getErrors()
    {
        return errors;
    }

}
