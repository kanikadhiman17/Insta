package com.java.zolo.instagram.controller;

import com.java.zolo.instagram.domain.dto.likes.LikesDTO;
import com.java.zolo.instagram.exceptions.IErrors;
import com.java.zolo.instagram.service.likes.LikesService;
import com.zolo.alpha.api.ResponseBody;
import com.zolo.alpha.api.ResponseGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LikesController {

     private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/users/{userId}/posts/{postId}/like")
    public ResponseBody likeAPost(@PathVariable long userId, @PathVariable long postId) {
        String errorCode = IErrors.FAILED_TO_LIKE_A_POST.getErrorCode();
        try {
            Optional<String> likeStatus = likesService.likeAPost(userId, postId);
            return likeStatus.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Post with ID "+postId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to like the post with id "+ postId);
        }
    }

    @PostMapping("/users/{userId}/comment/{commentId}/like")
    public ResponseBody likeAComment(@PathVariable long userId, @PathVariable long commentId) {
        String errorCode = IErrors.FAILED_TO_LIKE_A_COMMENT.getErrorCode();
        try {
            Optional<String> likeStatus = likesService.likeAComment(userId, commentId);
            return likeStatus.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Comment with ID "+commentId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to like the comment with id "+ commentId);
        }
    }

    @GetMapping("/posts/{postId}/likes")
    public ResponseBody getLikesOnAPost(@PathVariable long postId) {
        String errorCode = IErrors.FAILED_TO_FETCH_POST_LIKES.getErrorCode();
        try {
            Optional<List<LikesDTO>> fetchLikes = likesService.fetchLikesOnAPost(postId);
            return fetchLikes.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Post with ID "+postId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to fetch likes on the post with id " + postId);
        }
    }

    @GetMapping("/comments/{commentId}/likes")
    public ResponseBody getLikesOnAComment(@PathVariable long commentId) {
        String errorCode = IErrors.FAILED_TO_FETCH_COMMENT_LIKES.getErrorCode();
        try {
            Optional<List<LikesDTO>> fetchLikes = likesService.fetchLikesOnAComment(commentId);
            return fetchLikes.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Comment with ID "+commentId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to fetch likes on the comment with id " + commentId);
        }
    }

    @DeleteMapping("/users/{userId}/posts/{postId}/like")
    public ResponseBody unlikeAPost(@PathVariable long userId, @PathVariable long postId) {
        String errorCode = IErrors.FAILED_TO_UNLIKE_POST.getErrorCode();
        try {
            Optional<String> fetchLikes = likesService.unlikeAPost(userId, postId);
            return fetchLikes.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Like with provided user-post combination does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to unlike post with id " + postId);
        }
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}/like")
    public ResponseBody unlikeAComment(@PathVariable long userId, @PathVariable long commentId) {
        String errorCode = IErrors.FAILED_TO_UNLIKE_COMMENT.getErrorCode();
        try {
            Optional<String> fetchLikes = likesService.unlikeAComment(userId, commentId);
            return fetchLikes.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Like with provided user-comment combination does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to unlike comment with id " + commentId);
        }
    }

}
