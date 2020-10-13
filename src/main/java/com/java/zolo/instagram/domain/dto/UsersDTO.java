package com.java.zolo.instagram.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private String userName;
    private String emailId;
    private String password;
    private String profileName;
    private String bio;
    private Date dob;
    private String contactNo;
}
