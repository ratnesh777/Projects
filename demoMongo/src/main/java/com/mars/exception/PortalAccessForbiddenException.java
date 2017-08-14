package com.mars.exception;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PortalAccessForbiddenException extends ContextedRuntimeException
{

    private static final long serialVersionUID = 1L;
    private Errors errors;

    public PortalAccessForbiddenException(String message, Errors errors)
    {
        super(message);
        this.errors = errors;
    }

    public PortalAccessForbiddenException(String message)
    {
        super(message);
    }

    public Errors getErrors()
    {
        return errors;
    }
}
