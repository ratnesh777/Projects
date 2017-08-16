package com.mars.error;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mars.exception.InvalidRequestException;
import com.mars.exception.PortalAccessForbiddenException;
import com.mars.exception.PortalAuthenticationException;
import com.mars.exception.PortalException;
import com.mars.exception.PortalRequestDataTypeException;
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.util.ErrorMessagesConstant;


@ControllerAdvice
public class PortalExceptionHandler extends ResponseEntityExceptionHandler
{

    @ExceptionHandler({ InvalidRequestException.class })
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request)
    {
        InvalidRequestException ire = (InvalidRequestException) e;
        List<FieldErrorResource> fieldErrorResources = new ArrayList<>();

        List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
        for (FieldError fieldError : fieldErrors)
        {
            FieldErrorResource fieldErrorResource = new FieldErrorResource();
            fieldErrorResource.setResource(fieldError.getObjectName());
            fieldErrorResource.setField(fieldError.getField());
            fieldErrorResource.setCode(fieldError.getCode());
            fieldErrorResource.setMessage(fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }

        ErrorResource error = new ErrorResource(ErrorMessagesConstant.INVALID_REQUEST,
                ire.getMessage());
        error.setFieldErrors(fieldErrorResources);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler({ PortalServiceParameterException.class })
    protected ResponseEntity<Object> handleDuplicateRequest(RuntimeException e, WebRequest request)
    {
        PortalServiceParameterException ire = (PortalServiceParameterException) e;

        ErrorResource error = new ErrorResource(ErrorMessagesConstant.INVALID_PARAMETER_PASSED);
        error.setErrorContext(getErrorContext(ire));
        error.setMessage(ire.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler({ PortalAuthenticationException.class })
    protected ResponseEntity<Object> handleAutheticationException(RuntimeException e,
            WebRequest request)
    {
        PortalAuthenticationException ire = (PortalAuthenticationException) e;

        ErrorResource error = new ErrorResource(
                ErrorMessagesConstant.PORTAL_AUTHENTICATION_EXCEPTION, ire.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({ PortalAccessForbiddenException.class })
    protected ResponseEntity<Object> handleForbiddenRequest(RuntimeException e, WebRequest request)
    {
        PortalAccessForbiddenException ire = (PortalAccessForbiddenException) e;
        ErrorResource error = new ErrorResource(
                ErrorMessagesConstant.PORTAL_ACESS_FORBIDEEN_EXCEPTION, ire.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = PortalException.class)
    public ResponseEntity<Object> defaultErrorHandler(RuntimeException e, WebRequest request)
            throws Exception
    {
        PortalException portalException = (PortalException) e;

        ErrorResource error = new ErrorResource(ErrorMessagesConstant.INTERNAL_SERVER_ERROR);
        error.setErrorContext(getErrorContext(portalException));
        error.setMessage(portalException.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request)
    {
        ErrorResource error = new ErrorResource(ErrorMessagesConstant.INVALID_REQUEST,
                ex.getMessage());
        error.setErrorContext(null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, error, headers, HttpStatus.NOT_FOUND, request);
    }

    @Override
    @ExceptionHandler(value = { PortalRequestDataTypeException.class })
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request)
    {

        ErrorResource error = new ErrorResource(
                "Parameter datatype passed in the request is invalid", ex.getMessage());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<ErrorContextResource> getErrorContext(ContextedRuntimeException e)
    {
        if (e == null || CollectionUtils.isEmpty(e.getContextEntries()))
        {
            return null;
        }
        List<ErrorContextResource> errorContext = new ArrayList<>();
        for (Pair<String, Object> contextEntry : e.getContextEntries())
        {

            ErrorContextResource errorContextItem = new ErrorContextResource(contextEntry.getKey(),
                    contextEntry.getValue() != null ? contextEntry.getValue().toString() : null);
            errorContext.add(errorContextItem);
        }
        e.getContextEntries().clear();
        return errorContext;
    }

}
