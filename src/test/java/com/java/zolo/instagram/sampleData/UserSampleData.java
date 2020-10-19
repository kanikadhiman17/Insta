package com.java.zolo.instagram.sampleData;

import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.domain.model.User;

public class UserSampleData {

    public static UserDTO generateUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Test");
        userDTO.setEmailId("test@gmail.com");
        userDTO.setProfileName("Test User");
        userDTO.setPassword("abc");
        return userDTO;
    }

    public static User generateUser() {
        User user = new User();
        user.setUserName("Test");
        user.setEmailId("test@gmail.com");
        user.setProfileName("Test User");
        user.setPassword("abc");
        return user;
    }

    public static UserDTO generateReferenceUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Test1");
        userDTO.setEmailId("test1@gmail.com");
        userDTO.setProfileName("Test1 User");
        userDTO.setPassword("abcd");
        return userDTO;
    }
}
