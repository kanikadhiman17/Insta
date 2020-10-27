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
public class UserServiceUnitTest {

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
        given(usersRepository.save(user)).willReturn(user); // mocking repository's behavior

        // act
        UserDTO actualUserDTO = userService.createUser(userDTO);

        // assert
        assertEquals(userDTO.getUserName(), actualUserDTO.getUserName());
        verify(usersRepository).save(any(User.class));
    }

    @Test
    public void fetchUserFromId_success() {
        User user = UserSampleData.generateUser();

        doReturn(Optional.of(user))
                .when(usersRepository)
                .findById(user.getId());

        Optional<UserDTO> optionalUserDTO = userService.getUserFromId(user.getId());
        assertEquals(user.getUserName(), optionalUserDTO.get().getUserName());
        assertEquals(user.getEmailId(), optionalUserDTO.get().getEmailId());
        assertEquals(user.getProfileName(), optionalUserDTO.get().getProfileName());
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
    public void updateUser_success() {
        UserDTO userDTO = UserSampleData.generateUserDTO();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(userDTO, User.class);

        doReturn(Optional.of(user)).when(usersRepository).findById(user.getId()); // usersRepository.findById returns optional user
        doReturn(user).when(usersRepository).save(user); // usersRepository.save returns user

        Optional<UserDTO> optionalUserDTO = userService.updateUser(user.getId(), userDTO);
        assertEquals(userDTO.getUserName(), optionalUserDTO.get().getUserName());
        assertEquals(userDTO.getEmailId(), optionalUserDTO.get().getEmailId());
        assertEquals(userDTO.getProfileName(), optionalUserDTO.get().getProfileName());
    }

    @Test
    public void updateUser_failure() {
        UserDTO userDTO = UserSampleData.generateUserDTO();

        doReturn(Optional.empty()).when(usersRepository).findById(123456l); // usersRepository.findById returns optional user

        Optional<UserDTO> optionalUserDTO = userService.updateUser(123456l, userDTO);
        assertEquals(Optional.empty(), optionalUserDTO);
    }

    @Test
    public void deleteUser_success() {
        User user = UserSampleData.generateUser();

        doReturn(Optional.of(user)).when(usersRepository).findById(user.getId());

        Optional<String> deleteStatus = userService.deleteUser(user.getId());
        assertEquals("Sorry to let you go " + user.getProfileName(), deleteStatus.get());
    }

    @Test
    public void deleteUser_failure() {
        doReturn(Optional.empty()).when(usersRepository).findById(123456l);

        Optional<String> deleteStatus = userService.deleteUser(123456l);
        assertEquals(Optional.empty(), deleteStatus);
    }
}
