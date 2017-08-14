package com.mars.exception;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
@SuppressWarnings("serial")
public class PortalServiceParameterException extends ContextedRuntimeException
{

    private Errors errors;

    public PortalServiceParameterException(String message, Errors errors)
    {
        super(message);
        this.errors = errors;
    }

    public PortalServiceParameterException(String message)
    {
        super(message);
    }

    public Errors getErrors()
    {
        return errors;
    }

}
