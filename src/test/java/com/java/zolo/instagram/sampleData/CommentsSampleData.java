package com.java.zolo.instagram.sampleData;

import com.java.zolo.instagram.domain.dto.comments.CommentsDTO;

public class CommentsSampleData {

    public static CommentsDTO generateCommentsDTO(){
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setContent("Test Comment");
        return commentsDTO;
    }
}
