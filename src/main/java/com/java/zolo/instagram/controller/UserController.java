package com.java.zolo.instagram.controller;

import com.java.zolo.instagram.domain.dto.UsersDTO;
import com.java.zolo.instagram.exceptions.IErrors;
import com.java.zolo.instagram.service.user.UserService;
import com.zolo.alpha.api.ResponseBody;
import com.zolo.alpha.api.ResponseGenerator;
import com.zolo.alpha.exceptionhandling.ZoloException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    public final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseBody createUser(@Valid @RequestBody UsersDTO userRequest) {
        String errorCode = IErrors.FAILED_TO_CREATE_USER.getErrorCode();

        try {
            return ResponseGenerator.createSuccessResponse(userService.createUser(userRequest));
        } catch (ZoloException e) {
            return ResponseGenerator.createFailureResponseFromZoloException(e);
        } catch (Exception ex) {
            return ResponseGenerator.createFailureResponse(ex.getMessage(), errorCode,
                    "Failed to complete user creation");
        }
    }
}
