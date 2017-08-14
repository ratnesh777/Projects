package com.mars.exception;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class PortalException extends ContextedRuntimeException
{

    private static final long serialVersionUID = 1L;
    private String errorCode = "";

    public PortalException()
    {
        super();
    }

    public PortalException(Throwable cause)
    {
        super(cause);
    }

    public PortalException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public PortalException(String error)
    {
        super(error);
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

}
