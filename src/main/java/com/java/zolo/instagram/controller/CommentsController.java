package com.java.zolo.instagram.controller;

import com.java.zolo.instagram.domain.dto.comments.CommentsDTO;
import com.java.zolo.instagram.domain.dto.comments.PostedCommentDTO;
import com.java.zolo.instagram.domain.dto.comments.PostedReplyDTO;
import com.java.zolo.instagram.exceptions.IErrors;
import com.java.zolo.instagram.service.comments.CommentsService;
import com.zolo.alpha.api.ResponseBody;
import com.zolo.alpha.api.ResponseGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/users/{userId}/posts/{postId}/comments")
    public ResponseBody postComment(@RequestBody CommentsDTO commentsDTO, @PathVariable long userId, @PathVariable long postId) {
        String errorCode = IErrors.FAILED_TO_POST_COMMENT.getErrorCode();
        try {
            Optional<PostedCommentDTO> postedCommentDTO = commentsService.postComment(commentsDTO, userId, postId);
            return postedCommentDTO.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Post with ID "+postId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to comment on post with id "+ postId);
        }
    }

    @PostMapping("/users/{userId}/comments/{commentId}")
    public ResponseBody replyToAComment(@RequestBody CommentsDTO commentsDTO, @PathVariable long commentId, @PathVariable long userId) {
        String errorCode = IErrors.FAILED_TO_REPLY_THE_COMMENT.getErrorCode();
        try {
            Optional<PostedReplyDTO> postedReplyDTO = commentsService.replyToAComment(commentsDTO, userId, commentId);
            return postedReplyDTO.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Comment with ID "+commentId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to reply on the comment with id "+ commentId);
        }
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseBody showParentCommentsOnAPost(@PathVariable long postId) {
        String errorCode = IErrors.FAILED_TO_FETCH_PARENT_COMMENTS.getErrorCode();
        try {
            Optional<List<PostedCommentDTO>> postedCommentDTOList = commentsService.fetchParentComments(postId);
            return postedCommentDTOList.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Post with ID "+postId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to fetch parent comments on post with id "+ postId);
        }
    }

    @GetMapping("/comments/{commentId}")
    public ResponseBody showReplyOnAComment(@PathVariable long commentId) {
        String errorCode = IErrors.FAILED_TO_FETCH_REPLY_COMMENTS.getErrorCode();
        try {
            Optional<List<PostedReplyDTO>> postedReplyDTOList = commentsService.fetchReplyComments(commentId);
            return postedReplyDTOList.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Comment with ID "+commentId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to fetch reply comments on comment id "+ commentId);
        }
    }

    @PutMapping("/comments/{commentId}")
    public ResponseBody updateComment(@PathVariable long commentId, @RequestBody CommentsDTO commentDTO) {
        String errorCode = IErrors.FAILED_TO_UPDATE_COMMENT.getErrorCode();
        try {
            Optional<PostedCommentDTO> postedCommentDTO = commentsService.updateComment(commentId, commentDTO);
            return postedCommentDTO.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Comment with ID "+commentId+ " does not exist."));
        }   catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to update comments with id "+ commentId);
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseBody deleteComment(@PathVariable long commentId) {
        String errorCode = IErrors.FAILED_TO_DELETE_COMMENT.getErrorCode();
        try {
            Optional<String> deleteStatus = commentsService.deleteComment(commentId);
            return deleteStatus.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Comment with ID " + commentId + " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to delete comments with id " + commentId);
        }
    }
}
