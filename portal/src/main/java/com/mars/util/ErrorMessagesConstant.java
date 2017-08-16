package com.mars.util;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava 
 */

public class ErrorMessagesConstant {

	public static final String ID = "id";
	public static final String EMAIL_ID = "emailId";
	public static final String FIELD_NAME = "Field Name";
	public static final String RESOURCE_NOT_FOUND = "Resource not found.";
    public static final String INTERNAL_SERVER_ERROR_MSG = "We have encountered an internal error. Please try later.";
	public static final String INVALID_PARAMETER_PASSED  = "Parameters passed in the request is invalid.";
	public static final String INVALID_CREATE_REQUEST  = "Invalid create request";

    //swagger constants error messages
	public static final String SUCCESS = "Success";
	public static final String BAD_REQUEST = "Bad Request";
	public static final String INVALID_REQUEST = "Invalid Request";
	public static final String NOT_FOUND = "Not Found";
	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String NO_CONTENT ="No Content";
	public static final String OK ="OK";
	public static final String CREATED ="CREATED";

    //security
	public static final String PORTAL_AUTHENTICATION_EXCEPTION = "Authentication Error|Invalid Credentials";
	public static final String INVALID_CREDENTIALS = "Invalid emailId/password combination passed in request";
	public static final String PORTAL_ACESS_FORBIDEEN_EXCEPTION = "Access Forbidden";
	public static final String ACCESS_FORBIDDEN = "Not autohirized due to system access restriction";

	//company
    public static final String DUPLICATE_COMPANY_NAME = "Company name already exist";
    public static final String COMPANY_NOT_FOUND = "Could not find company by id";

    //user
    public static final String DUPLICATE_USER_EMAIL = "User email already exist";
    public static final String ERROR_CREATE_USER = "Could not create user";
    public static final String USER_ID_NOT_FOUND = "Could not find User by id";
    public static final String EMAIL_ID_NOT_FOUND = "Could not find User by emailId";
    public static final String ROLE_ID_NOT_FOUND = "Could not find Role by id";
    public static final String PRODUCT_ID_NOT_FOUND = "Could not find Product by id";
    public static final String PASSWORD_LENGTH_NOT_ENOUGH =  "Password must be at least 8 characters";
    public static final String PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_CAPITAL_LETTER = "Password must contain at least one capital letter";
    public static final String PASSWORD_MUST_CONTAIN_AT_LEAST_ONE_NUMER = "Password must contain at least one number";
    public static final String NAME_MUST_BE_VALID_EMAIL_ADDRESS ="username must be valid email address";

    //back room
    public static final String DUPLICATE_BACK_ROOM_NAME_AND_COMPANY = "Back room name already exist in the company";
    public static final String ERROR_CREATE_BACK_ROOM = "Could not create back room";
    public static final String BACK_ROOM_ID_NOT_FOUND = "Could not find back room by id";

}