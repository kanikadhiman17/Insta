package com.java.zolo.instagram.domain.dto.likes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesDTO {
    private String userName;
    private Timestamp timestamp;
}
