package com.java.zolo.instagram.mapper;

import com.java.zolo.instagram.domain.dto.UserDTO;
import com.java.zolo.instagram.domain.model.User;

public class MapperUtil {
    public static User buildUserModel(UserDTO usersDTO){
        User user = new User();
        user.setUserName(usersDTO.getUserName());
        user.setEmailId(usersDTO.getEmailId());
        user.setPassword(usersDTO.getPassword());
        user.setProfileName(usersDTO.getProfileName());
        user.setBio(usersDTO.getBio());
        user.setDob(usersDTO.getDob());
        user.setContactNo(usersDTO.getContactNo());

        return user;
    }
}
