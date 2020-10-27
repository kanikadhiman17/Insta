package com.java.zolo.instagram.sampleData;

import com.java.zolo.instagram.domain.dto.comments.CommentsDTO;
import com.java.zolo.instagram.domain.model.Comments;
import com.java.zolo.instagram.domain.model.Posts;
import com.java.zolo.instagram.domain.model.User;

public class CommentsSampleData {

    public static CommentsDTO generateCommentsDTO(){
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setContent("Test Comment");
        return commentsDTO;
    }

    public static Comments generateComment() {
        Comments comment = new Comments();
        User user = UserSampleData.generateUser();
        Posts post = PostsImagesSampleData.generatePost();
        comment.setId(200l);
        comment.setContent("Test Comment");
        comment.setUser(user);
        comment.setPost(post);
        return comment;
    }

    public static Comments generateReplyComment() {
        Comments replyComment = new Comments();
        User user = UserSampleData.generateUser();
        Comments comment = CommentsSampleData.generateComment();
        replyComment.setId(300l);
        replyComment.setContent("Test Comment");
        replyComment.setUser(user);
        replyComment.setPost(comment.getPost());
        replyComment.setReply(comment);
        return replyComment;
    }
}
