package com.java.zolo.instagram.exceptions;

import com.zolo.alpha.exceptionhandling.Error;

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

    // Posts
    public static final Error FAILED_TO_UPLOAD_POST = new Error("Failed to upload post",1013);
    public static final Error FAILED_TO_FETCH_POST = new Error("Failed to fetch posts", 1014);
    public static final Error FAILED_TO_UPDATE_POST = new Error("Failed to update post", 1015);
    public static final Error FAILED_TO_DELETE_POST = new Error("Failed to delete post", 1016);

    // Comments
    public static final Error FAILED_TO_POST_COMMENT = new Error("Failed to post the comment", 1017);
    public static final Error FAILED_TO_REPLY_THE_COMMENT = new Error("Failed to reply on a comment", 1018);
    public static final Error FAILED_TO_FETCH_PARENT_COMMENTS = new Error("Failed to fetch patent comments", 1019);
    public static final Error FAILED_TO_FETCH_REPLY_COMMENTS = new Error("Failed to fetch reply comments", 1020);
    public static final Error FAILED_TO_UPDATE_COMMENT = new Error("Failed to update comment", 1021);
    public static final Error FAILED_TO_DELETE_COMMENT = new Error("Failed to delete comment", 1022);

    // Likes
    public static final Error FAILED_TO_LIKE_A_POST = new Error("Failed to like a post", 1023);
    public static final Error FAILED_TO_LIKE_A_COMMENT = new Error("Failed to like a comment", 1024);
    public static final Error FAILED_TO_FETCH_POST_LIKES = new Error("Failed to fetch post's likes", 1025);
    public static final Error FAILED_TO_FETCH_COMMENT_LIKES = new Error("Failed to fetch comment's likes", 1026);
    public static final Error FAILED_TO_UNLIKE_POST = new Error("Failed to unlike a post", 1027);
    public static final Error FAILED_TO_UNLIKE_COMMENT = new Error("Failed to unlike a comment", 1028);
}
