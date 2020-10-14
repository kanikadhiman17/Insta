package com.java.zolo.instagram.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterDTO {
    private String userName;
    private String emailId;
    private String profileName;
}
