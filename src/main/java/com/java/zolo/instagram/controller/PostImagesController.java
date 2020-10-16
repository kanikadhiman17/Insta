package com.java.zolo.instagram.controller;

import com.java.zolo.instagram.domain.dto.postImages.PostImagesDTO;
import com.java.zolo.instagram.domain.dto.postImages.PostUpdateDTO;
import com.java.zolo.instagram.exceptions.IErrors;
import com.java.zolo.instagram.service.postImages.PostImagesService;
import com.zolo.alpha.api.ResponseBody;
import com.zolo.alpha.api.ResponseGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PostImagesController {

    public final PostImagesService postImagesService;

    public PostImagesController(PostImagesService postImagesService) {
        this.postImagesService = postImagesService;
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseBody uploadPost(@RequestBody PostImagesDTO postImagesDTO, @PathVariable long userId) {
        String errorCode = IErrors.FAILED_TO_UPLOAD_POST.getErrorCode();
        try {
            Optional<PostImagesDTO> postImageDTO = postImagesService.uploadPostAndImages(postImagesDTO, userId);
            return postImageDTO.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Image List cannot be empty"));

        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to upload post");
        }
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseBody getPostsFromUser(@PathVariable long userId)  {
        String errorCode = IErrors.FAILED_TO_FETCH_POST.getErrorCode();
        try {
            Optional<List<PostImagesDTO>> postImagesDTOList = postImagesService.fetchPostsFromUser(userId);
            return postImagesDTOList.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("User with id " +userId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to upload post");
        }
    }

    @GetMapping("/posts/{postId}")
    public ResponseBody getPostsFromId(@PathVariable long postId)  {
        String errorCode = IErrors.FAILED_TO_FETCH_POST.getErrorCode();
        try {
            Optional<PostImagesDTO> postImagesDTO = postImagesService.fetchPostsFromId(postId);
            return postImagesDTO.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Post with id " +postId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to upload post");
        }
    }

    @PatchMapping("/posts/{postId}")
    public ResponseBody editPost(@PathVariable long postId, @RequestBody PostUpdateDTO postUpdateDTO) {
        String errorCode = IErrors.FAILED_TO_UPDATE_POST.getErrorCode();
        try {
            Optional<PostImagesDTO> postImagesDTO = postImagesService.editPost(postId, postUpdateDTO);
            return postImagesDTO.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Post with id " +postId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to upload post");
        }
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseBody deletePost(@PathVariable long postId) {
        String errorCode = IErrors.FAILED_TO_DELETE_POST.getErrorCode();
        try {
            Optional<String> deleteStatus = postImagesService.deletePost(postId);
            return deleteStatus.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("Post with id " +postId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to upload post");
        }
    }
}
