package com.java.zolo.instagram.exceptions;

import com.zolo.alpha.exceptionhandling.Error;

import java.sql.SQLException;

public class IErrors {

    public static final Error INTERNAL_SERVER_ERROR = new Error("A server error occurred.", 1000);

    public static final Error INVALID_REQUEST = new Error("Invalid request", 1001);

    public static final Error INVALID_RESPONSE = new Error("Invalid response", 1002);

    public static final Error FAILED_TO_CREATE_USER = new Error("Failed to  a new User",1003);

    public static final Error PARSING_ERROR = new Error("Error while parsing response",  1008);

    public static final Error FAILED_TO_FETCH_USER_FROM_ID = new Error("Failed to fetch user from User ID", 1004);

    public static final Error FAILED_TO_FETCH_USER = new Error("Failed to fetch user from Query Parameters", 1005);

    public static final Error FAILED_TO_UPDATE_USER = new Error("Failed to update user", 1006);

    public static final Error FAILED_TO_DELETE_USER = new Error("Failed to delete user", 1007);
}
