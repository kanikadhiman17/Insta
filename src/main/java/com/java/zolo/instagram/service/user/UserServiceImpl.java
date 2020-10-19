package com.java.zolo.instagram.service.user;

import com.java.zolo.instagram.domain.criteria.BaseSpecification;
import com.java.zolo.instagram.domain.criteria.SearchCriteria;
import com.java.zolo.instagram.domain.criteria.SearchOperation;
import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.domain.dto.user.UserFilterDTO;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    public final UsersRepository usersRepository;
    ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Override
    public UserDTO createUser(UserDTO userRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        usersRepository.save(modelMapper.map(userRequest, User.class));
        return userRequest;
    }

    @Override
    public Optional<UserDTO> getUserFromId(long userId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        return optionalUser.map(user -> Optional.of(modelMapper.map(user, UserDTO.class))).orElse(null);
    }

    @Override
    public List<UserDTO> getUserFromQueryParameters(UserFilterDTO filterDTO) throws NoSuchFieldException {
        BaseSpecification<User> userCriteria = new BaseSpecification<>();
        userCriteria.add(new SearchCriteria(User.class.getDeclaredField("userName").getName(), filterDTO.getUserName(), SearchOperation.MATCH));
        userCriteria.add(new SearchCriteria(User.class.getDeclaredField("emailId").getName(), filterDTO.getEmailId(), SearchOperation.MATCH));
        userCriteria.add(new SearchCriteria(User.class.getDeclaredField("profileName").getName(), filterDTO.getProfileName(), SearchOperation.MATCH));
        List<User> matchedUsers = usersRepository.findAll(userCriteria);
        return matchedUsers.stream().map(p -> modelMapper.map(p, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> updateUser(long userId, UserDTO userDTO) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        if(optionalUser.isEmpty())
            return Optional.empty();

        User user = optionalUser.get();
        if (userDTO.getUserName() != null) {
            user.setUserName(userDTO.getUserName());
        }
        if (userDTO.getEmailId() != null) {
            user.setEmailId(userDTO.getEmailId());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        if (userDTO.getProfileName() != null) {
            user.setProfileName(userDTO.getProfileName());
        }
        if (userDTO.getBio() != null) {
            user.setBio(userDTO.getBio());
        }
        if (userDTO.getDob() != null) {
            user.setDob(userDTO.getDob());
        }
        if (userDTO.getContactNo() != null) {
            user.setContactNo(userDTO.getContactNo());
        }

        usersRepository.save(user);
        return Optional.of(modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Optional<String> deleteUser(long userId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        if(optionalUser.isEmpty())
            return Optional.empty();
        User user = optionalUser.get();
        usersRepository.deleteById(userId);
        return Optional.of("Sorry to let you go " + user.getProfileName());
    }
}
