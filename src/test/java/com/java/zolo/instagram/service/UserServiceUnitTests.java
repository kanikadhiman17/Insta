package com.java.zolo.instagram.service;

import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.UsersRepository;
import com.java.zolo.instagram.sampleData.UserSampleData;
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
import static org.mockito.BDDMockito.*;

import java.util.Optional;

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
        // arrange
        UserDTO userDTO = UserSampleData.generateUserDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(userDTO, User.class);
        String expectedUserName = "test_user";
        given(usersRepository.save(user)).willReturn(user); // mocking repository's behavior

        // act
        UserDTO actualUserDTO = userService.createUser(userDTO);

        // assert
        assertEquals(expectedUserName, actualUserDTO.getUserName());
        verify(usersRepository).save(any(User.class));
    }

    @Test
    public void fetchUserFromId_success() {
        User user = UserSampleData.generateUser();

        String expectedUserName = "test_user";
        String expectedEmailId = "test@gmail.com";
        String expectedProfileName = "Test User";

        doReturn(Optional.of(user))
                .when(usersRepository)
                .findById(12345l);

        Optional<UserDTO> optionalUserDTO = userService.getUserFromId(12345l);
        assertEquals(expectedUserName, optionalUserDTO.get().getUserName());
        assertEquals(expectedEmailId, optionalUserDTO.get().getEmailId());
        assertEquals(expectedProfileName, optionalUserDTO.get().getProfileName());
    }

    @Test
    public void fetchUserFromId_failure() {
        doReturn(Optional.empty())
                .when(usersRepository)
                .findById(123456l);

        Optional<UserDTO> optionalUserDTO = userService.getUserFromId(123456l);
        assertEquals(Optional.empty(), optionalUserDTO);
    }

    @Test
    public void updateUserWhenIdIsPresent() {
        UserDTO userDTO = UserSampleData.generateUserDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(userDTO, User.class);

        String expectedUserName = "test_user";
        String expectedEmailId = "test@gmail.com";
        String expectedProfileName = "Test User";

        doReturn(Optional.of(user)).when(usersRepository).findById(12345l); // usersRepository.findById returns optional user
        doReturn(user).when(usersRepository).save(user); // usersRepository.save returns user

        Optional<UserDTO> optionalUserDTO = userService.updateUser(12345l, userDTO);
        assertEquals(expectedUserName, optionalUserDTO.get().getUserName());
        assertEquals(expectedEmailId, optionalUserDTO.get().getEmailId());
        assertEquals(expectedProfileName, optionalUserDTO.get().getProfileName());
    }

    @Test
    public void updateUserWhenIdIsNotPresent() {
        UserDTO userDTO = UserSampleData.generateUserDTO();

        doReturn(Optional.empty()).when(usersRepository).findById(12345l); // usersRepository.findById returns optional user

        Optional<UserDTO> optionalUserDTO = userService.updateUser(12345l, userDTO);
        assertEquals(Optional.empty(), optionalUserDTO);
    }

    @Test
    public void deleteUserWhenIdIsPresent() {
        User user = UserSampleData.generateUser();

        doReturn(Optional.of(user)).when(usersRepository).findById(12345l);

        Optional<String> deleteStatus = userService.deleteUser(12345l);
        assertEquals("Sorry to let you go " + user.getProfileName(), deleteStatus.get());
    }

    @Test
    public void deleteUserWhenIdIsNotPresent() {
        doReturn(Optional.empty()).when(usersRepository).findById(12345l);

        Optional<String> deleteStatus = userService.deleteUser(12345l);
        assertEquals(Optional.empty(), deleteStatus);
    }
}
