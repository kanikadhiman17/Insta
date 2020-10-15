package com.java.zolo.instagram.exceptions;

import com.zolo.alpha.exceptionhandling.Error;

import java.sql.SQLException;

public class IErrors {

    public static final Error INTERNAL_SERVER_ERROR = new Error("A server error occurred.", 1000);
    public static final Error INVALID_REQUEST = new Error("Invalid request", 1001);
    public static final Error INVALID_RESPONSE = new Error("Invalid response", 1002);
    public static final Error PARSING_ERROR = new Error("Error while parsing response",  1003);

    // Users
    public static final Error FAILED_TO_CREATE_USER = new Error("Failed to create a new User",1004);
    public static final Error FAILED_TO_FETCH_USER_FROM_ID = new Error("Failed to fetch user from User ID", 1005);
    public static final Error FAILED_TO_FETCH_USER = new Error("Failed to fetch user from Query Parameters", 1006);
    public static final Error FAILED_TO_UPDATE_USER = new Error("Failed to update user", 1007);
    public static final Error FAILED_TO_DELETE_USER = new Error("Failed to delete user", 1008);

    // Follow Map
    public static final Error FAILED_TO_FOLLOW = new Error("Failed to follow",1009);
    public static final Error FAILED_TO_FETCH_FOLLOWING = new Error("Failed to fetch following",1010);
    public static final Error FAILED_TO_FETCH_FOLLOWERS = new Error("Failed to fetch followers",1011);
    public static final Error FAILED_TO_UNFOLLOW = new Error("Failed to unfollow",1012);
}
