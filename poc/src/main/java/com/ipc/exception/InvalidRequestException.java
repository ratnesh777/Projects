package com.ipc.exception;

import org.springframework.validation.Errors;

//@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException {

    private Errors errors;

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

}
