package com.java.zolo.instagram.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDTO {
    private String userName;
    private String emailId;
    private String password;
    private String profileName;
    private String bio;
    private Date dob;
    private String contactNo;
}