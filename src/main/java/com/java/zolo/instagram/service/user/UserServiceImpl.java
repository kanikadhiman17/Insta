package com.java.zolo.instagram.service.user;

import com.java.zolo.instagram.domain.dto.UsersDTO;
import com.java.zolo.instagram.mapper.MapperUtil;
import com.java.zolo.instagram.repository.UsersRepository;
import com.zolo.alpha.exceptionhandling.ZoloException;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class UserServiceImpl implements UserService {
    public final UsersRepository usersRepository;


    public UserServiceImpl(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public UsersDTO createUser(UsersDTO userRequest) throws ZoloException, ParseException {

        usersRepository.save(MapperUtil.buildUserModel(userRequest));
        return userRequest;
    }
}
