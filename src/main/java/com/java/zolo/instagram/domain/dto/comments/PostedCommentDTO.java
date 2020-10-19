package com.java.zolo.instagram.domain.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostedCommentDTO {
    private long postId;
    private String userName;
    private String content;
    private Timestamp timestamp;
}
