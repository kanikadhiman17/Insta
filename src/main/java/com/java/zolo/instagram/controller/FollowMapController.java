package com.java.zolo.instagram.controller;

import com.java.zolo.instagram.domain.dto.followMap.FollowMapDTO;
import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.exceptions.IErrors;
import com.java.zolo.instagram.service.followmap.FollowMapService;
import com.zolo.alpha.api.ResponseBody;
import com.zolo.alpha.api.ResponseGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class FollowMapController {

    private final FollowMapService followMapService;

    public FollowMapController(FollowMapService followMapService) {
        this.followMapService = followMapService;
    }

    @PostMapping("/users/{userId}/follow/{targetId}")
    public ResponseBody followEvent(@PathVariable long userId, @PathVariable long targetId) {
        String errorCode = IErrors.FAILED_TO_FOLLOW.getErrorCode();
        try {
            if(userId == targetId)
                return ResponseGenerator.createSuccessResponse("A user cannot follow oneself");
            Optional<FollowMapDTO> followMapDTO = followMapService.follow(userId,targetId);
            return followMapDTO.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("User with ID "+targetId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    userId + " Failed to follow "+ targetId);
        }
    }

    @GetMapping("/users/{userId}/follows")
    public ResponseBody getFollowing(@PathVariable long userId){
        String errorCode = IErrors.FAILED_TO_FETCH_FOLLOWING.getErrorCode();
        try {
            Optional<List<UserDTO>> following = followMapService.getFollowing(userId);
            return  following.map(ResponseGenerator::createSuccessResponse).orElseGet(() -> ResponseGenerator.createSuccessResponse("User with id " + userId + " not found."));
        }  catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                     "Failed to fetch 'following' for the user with id "+userId);
        }
    }

    @GetMapping("/users/{userId}/followed-by")
    public ResponseBody getFollowers(@PathVariable long userId){
        String errorCode = IErrors.FAILED_TO_FETCH_FOLLOWERS.getErrorCode();
        try {
            Optional<List<UserDTO>> followers = followMapService.getFollowers(userId);
            return  followers.map(ResponseGenerator::createSuccessResponse).orElseGet(() -> ResponseGenerator.createSuccessResponse("User with id " + userId + " not found."));
        }  catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to fetch 'followers' for the user with id "+userId);
        }
    }

    @DeleteMapping("/users/{userId}/follow/{targetId}")
    public ResponseBody unfollowEvent(@PathVariable long userId, @PathVariable long targetId) {
        String errorCode = IErrors.FAILED_TO_UNFOLLOW.getErrorCode();
        try {
            Optional<String> unfollowUser = followMapService.unfollow(userId,targetId);
            return unfollowUser.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("User with ID "+targetId+ " does not exist."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    userId + " Failed to unfollow "+ targetId);
        }
    }
}
