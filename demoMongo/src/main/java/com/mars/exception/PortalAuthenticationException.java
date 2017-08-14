package com.mars.exception;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class PortalAuthenticationException extends ContextedRuntimeException
{

    private static final long serialVersionUID = 1L;
    private Errors errors;

    public PortalAuthenticationException(String message, Errors errors)
    {
        super(message);
        this.errors = errors;
    }

    public PortalAuthenticationException(String message)
    {
        super(message);
    }

    public Errors getErrors()
    {
        return errors;
    }
}
