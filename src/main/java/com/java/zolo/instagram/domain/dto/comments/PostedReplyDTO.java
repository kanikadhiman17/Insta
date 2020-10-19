package com.java.zolo.instagram.domain.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PostedReplyDTO {
    private long postId;
    private long parentCommentId;
    private String userName;
    private String content;
    private Timestamp postedAt;
}
