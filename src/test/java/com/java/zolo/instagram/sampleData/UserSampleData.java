package com.java.zolo.instagram.sampleData;

import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.domain.model.User;

public class UserSampleData {

    public static UserDTO generateUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("test_user");
        userDTO.setEmailId("test@gmail.com");
        userDTO.setProfileName("Test User");
        userDTO.setPassword("abc");
        return userDTO;
    }

    public static User generateUser() {
        User user = new User();
        user.setId(12345l);
        user.setUserName("test_user");
        user.setEmailId("test@gmail.com");
        user.setProfileName("Test User");
        user.setPassword("abc");
        return user;
    }

    public static User generateFollower() {
        User user = new User();
        user.setId(1234l);
        user.setUserName("follower_user");
        user.setEmailId("test1@gmail.com");
        user.setProfileName("Follower User");
        user.setPassword("abc");
        return user;
    }

    public static User generateTargetUser() {
        User user = new User();
        user.setId(123l);
        user.setUserName("target_user");
        user.setEmailId("test2@gmail.com");
        user.setProfileName("Target User");
        user.setPassword("abc");
        return user;
    }

}
