package com.java.zolo.instagram.service.user;

import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.domain.dto.user.UserFilterDTO;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO userRequest);

    Optional<UserDTO> getUserFromId(long userId);

    List<UserDTO> getUserFromQueryParameters(UserFilterDTO filterDTO) throws NoSuchFieldException;

    Optional<UserDTO> updateUser(long userId, UserDTO usersDTO);

    Optional<String> deleteUser(long userId);
}
