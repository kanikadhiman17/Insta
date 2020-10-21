package com.java.zolo.instagram.services;

import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.UsersRepository;
import com.java.zolo.instagram.service.user.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UsersRepository usersRepository;

    @Test
    public void createUser_success() {
        UserDTO userDTO = new UserDTO().setUserName("test_user")
                .setEmailId("test@gmail.com")
                .setPassword("abcxyz")
                .setProfileName("Test User");

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(userDTO, User.class);
        String expectedUserName = "test_user";

        UserDTO actualUserDTO = userService.createUser(userDTO);
        assertEquals(actualUserDTO.getUserName(), expectedUserName);
    }

}
