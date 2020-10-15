package com.java.zolo.instagram.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.domain.dto.user.UserFilterDTO;
import com.java.zolo.instagram.exceptions.IErrors;
import com.java.zolo.instagram.service.user.UserService;
import com.zolo.alpha.api.ResponseBody;
import com.zolo.alpha.api.ResponseGenerator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseBody createUser(@Valid @RequestBody UserDTO userRequest) {
        String errorCode = IErrors.FAILED_TO_CREATE_USER.getErrorCode();
        try {
            return ResponseGenerator.createSuccessResponse(userService.createUser(userRequest));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to complete user creation");
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseBody fetchUserFromId(@Valid @PathVariable long userId) {
        String errorCode = IErrors.FAILED_TO_FETCH_USER_FROM_ID.getErrorCode();
        try {
            return ResponseGenerator.createSuccessResponse(Objects.requireNonNullElseGet(
                    userService.getUserFromId(userId), () -> "User with id " + userId + " not found."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to fetch user from user id");
        }
    }

    @GetMapping("/users")
    public ResponseBody fetchUsers(@RequestParam Map<String, String > queryParams) {
        String errorCode = IErrors.FAILED_TO_FETCH_USER.getErrorCode();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
            UserFilterDTO filterDTO = mapper.convertValue(queryParams, UserFilterDTO.class);
            return ResponseGenerator.createSuccessResponse(userService.getUserFromQueryParameters(filterDTO));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to fetch user from query parameters");
        }
    }

    @PatchMapping("/users/{userId}")
    public ResponseBody updateUser(@Valid @PathVariable long userId, @RequestBody UserDTO userDTO) {
        String errorCode = IErrors.FAILED_TO_UPDATE_USER.getErrorCode();
        try {
            Optional<UserDTO> optionalUserDTO= userService.updateUser(userId, userDTO);
            return optionalUserDTO.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("User with id " + userId + " not found."));
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to update user");
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseBody deleteUser(@Valid @PathVariable long userId) {
        String errorCode = IErrors.FAILED_TO_DELETE_USER.getErrorCode();
        try {
            Optional<String> deleteUser = userService.deleteUser(userId);
            return deleteUser.map(ResponseGenerator::createSuccessResponse)
                    .orElseGet(() -> ResponseGenerator.createSuccessResponse("User with id " + userId + " not found."));

        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to delete user");
        }
    }
}