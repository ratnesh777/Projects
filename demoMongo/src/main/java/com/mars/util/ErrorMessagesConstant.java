package com.mars.util;


public class ErrorMessagesConstant
{

    public static final String ID = "id";
    public static final String IDS = "ids";
    public static final String NAME = "id";
    public static final String EMAIL_ID = "emailId";
    public static final String FIELD_NAME = "Field Name";
    public static final String RESOURCE_NOT_FOUND = "Resource not found.";
    public static final String INTERNAL_SERVER_ERROR_MSG = "We have encountered an internal error. Please try later.";
    public static final String INVALID_PARAMETER_PASSED = "Parameters passed in the request is invalid.";
    public static final String INVALID_CREATE_REQUEST = "Invalid create request";
    public static final String PASSWORD_TOKEN = "password token";
    public static final String INVALID_PASSWORD_TOKEN = "Invalid password token";

    // swagger constants error messages
    public static final String SUCCESS = "Success";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String INVALID_REQUEST = "Invalid Request";
    public static final String NOT_FOUND = "Not Found";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String NO_CONTENT = "No Content";
    public static final String OK = "OK";
    public static final String CREATED = "CREATED";

    // security
    public static final String PORTAL_AUTHENTICATION_EXCEPTION = "Authentication Error|Invalid Credentials";
    public static final String INVALID_CREDENTIALS = "Invalid emailId/password combination passed in request";
    public static final String INVALID_EMAIL = "EmailId not found";
    public static final String PORTAL_ACESS_FORBIDEEN_EXCEPTION = "Access Forbidden";
    public static final String ACCESS_FORBIDDEN = "Not authorized due to system access restriction, please reach out to Administrator.";
    public static final String ACCESS_FORBIDDEN_WRONG_ENDPOINT = "Kindly access application through exposed url, please reach out to Administrator in case of any queires.";
   

    // company
    public static final String DUPLICATE_CUSTOMER_NAME = "Customer name already exist";
    public static final String CUSTOMER_NOT_FOUND = "Could not find customer by id";

    // user
    public static final String DUPLICATE_USER_EMAIL = "User email already exist";
    public static final String ERROR_CREATE_USER = "Could not create user";
    public static final String USER_ID_NOT_FOUND = "Could not find User by id";
    public static final String USER_TOKEN_NOT_FOUND = "Could not find User by password token";
    
    public static final String DATACENTER_ID_NOT_FOUND = "Could not find DataCenter by id";
    public static final String DATACENTER_IDS_PASSED_NULL_WHEN_ISDELETEALL_PASSED = "Pass some values in ids when isDeleteAll param is false";
    public static final String DUPLICATE_DATACENTER_NAME = "DataCenter name already exist";

    public static final String EMAIL_ID_NOT_FOUND = "Could not find User by emailId";
    public static final String ROLE_ID_NOT_FOUND = "Could not find Role by id";
    public static final String PRODUCT_ID_NOT_FOUND = "Could not find Product by id";
    public static final String PASSWORD_LENGTH_NOT_ENOUGH = "Password must be at least 8 characters";
    public static final String PASSWORD_FORMAT_INVALID = "Password must contain at least 1 Uppercase Alphabet, 1 Lowercase Alphabet and 1 Number. Should Not have round brackets '()' and double quotes '\"'";
    public static final String EMAIL_CAN_NOT_UPDATED_FOR_REGISTERED_STATUS = "Email can not be updated for user with whose status is REGISTERED";
    public static final String CUSTOMER_PRODUCT_NOT_NULL = "Customer or Product cannot be null for Customer Admin/EndUser";
    public static final String USER_IDS_PASSED_NULL_WHEN_ISDELETEALL_PASSED = "Pass some values in ids when isDeleteAll param is false";
    public static final String USER_IDS_PASSED_NULL = "Pass some values in ids ";
    public static final String EMAIL_NOT_FOUND = "Email not found. Please reach out to Administrator";
    public static final String USER_NOT_FOUND = "Could not find user by id";
    // back room
    public static final String DUPLICATE_HOMEZONE_NAME = "HomeZone name already exist";
    public static final String ERROR_CREATE_HOMEZONE = "Could not create homeZone";
    public static final String HOMEZONE_ID_NOT_FOUND = "Could not find homeZone by id";
    public static final String HOMEZONE_NAME_NOT_FOUND = "HomeZone name not found";
    public static final String HOMEZONE_NOT_FOUND_FOR_CUSTOMER = "HomeZone not found for given customer";

    // endpoint
    public static final String END_POINT_NOT_FOUND = "Could not find endpoint by id";
    public static final String DUPLICATE_END_POINT = "Endpoint uniqueId already exist";
    public static final String END_POINT_CUSTOMER_NULL_WHEN_HOMEZONE_PASSED = "Customer can not be null when homeZone name paased in request";
    public static final String END_POINT_IDS_NULL_NULL_WHEN_ISDELETEALL_PASSED = "Pass some values in ids when isDeleteAll param is false";
    public static final String USER_IDS_NULL_NULL_WHEN_ISDELETEALL_PASSED = "Pass some values in ids when isSendAll param is false";
    public static final String END_POINT_HOME_ZONE_REQUIRED_PROVISION_DATA_MISSING = "Selected homeZone does not have required provisioning data.";
    public static final String LOGGED_IN_USER_CANNOT_BE_DEACTIVATED_ACTIVATED= "Logged in user cannot be deactivate/activate.";
	public static final String DATACENTER_NAME_NOT_FOUND = "DataCenter name not found";
	public static final String MAC_ADDRESS_NOT_FOUND = "MAC address not found";
	public static final String MAC_ADDRESS_NOT_VALID = "Please enter a valid MAC Address";

}
