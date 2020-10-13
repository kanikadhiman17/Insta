package com.java.zolo.instagram.service.user;

import com.java.zolo.instagram.domain.dto.UsersDTO;
import com.zolo.alpha.exceptionhandling.ZoloException;
import java.text.ParseException;
import java.util.List;

public interface UserService {
    UsersDTO createUser(UsersDTO userRequest) throws ZoloException, ParseException;
}
