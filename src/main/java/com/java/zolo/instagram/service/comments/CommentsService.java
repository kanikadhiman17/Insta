package com.java.zolo.instagram.service.comments;

import com.java.zolo.instagram.domain.dto.comments.CommentsDTO;
import com.java.zolo.instagram.domain.dto.comments.PostedCommentDTO;
import com.java.zolo.instagram.domain.dto.comments.PostedReplyDTO;

import java.util.List;
import java.util.Optional;

public interface CommentsService {
    Optional<PostedCommentDTO> postComment(CommentsDTO commentsDTO, long userId, long postId);
    Optional<PostedReplyDTO> replyToAComment(CommentsDTO commentsDTO, long userId, long commentId);
    Optional<List<PostedCommentDTO>> fetchComments(long postId);
    Optional<PostedCommentDTO> updateComment(long commentId, CommentsDTO commentDTO);
    Optional<String> deleteComment(long commentId);
}
