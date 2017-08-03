package com.ipc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason ="This employee is not found in the system")
public class EmpNotFoundException extends Exception {

    
    private static final long serialVersionUID = 1L;
    private String errorCode = "";
    public EmpNotFoundException(){
        super();
    }
    
    public EmpNotFoundException(String error) {
        super(error);
    }
    
    public EmpNotFoundException(Throwable t) {
		super(t);
	}

	public EmpNotFoundException(String errorMessage, Throwable t) {
		super(errorMessage, t);
	}	
    
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

	
}
