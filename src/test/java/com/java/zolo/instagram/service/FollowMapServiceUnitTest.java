package com.java.zolo.instagram.service;

import com.java.zolo.instagram.domain.dto.followMap.FollowMapDTO;
import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.domain.model.FollowMap;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.FollowMapRepository;
import com.java.zolo.instagram.repository.UsersRepository;
import com.java.zolo.instagram.sampleData.FollowMapSampleData;
import com.java.zolo.instagram.sampleData.UserSampleData;
import com.java.zolo.instagram.service.followMap.FollowMapServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class FollowMapServiceUnitTest {

    @InjectMocks
    FollowMapServiceImpl followMapService;

    @Mock
    FollowMapRepository followMapRepository;

    @Mock
    UsersRepository usersRepository;

    @Test
    public void follow_success() {
        User follower = UserSampleData.generateFollower();
        User targetUser = UserSampleData.generateTargetUser();
        FollowMap followMap = FollowMapSampleData.generateFollowMap();

        given(usersRepository.findById(follower.getId())).willReturn(Optional.of(follower));
        given(usersRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        given(followMapRepository.save(new FollowMap().setFollower(follower).setTargetUser(targetUser))).willReturn(followMap);

        Optional<FollowMapDTO> followMapDTO = followMapService.follow(follower.getId(), targetUser.getId());

        assertEquals(follower.getUserName(), followMapDTO.get().getFollower().getUserName());
        assertEquals(targetUser.getUserName(), followMapDTO.get().getTargetUser().getUserName());

    }

    @Test
    public void follow_failure() {
        User follower = UserSampleData.generateFollower();

        given(usersRepository.findById(follower.getId())).willReturn(Optional.of(follower));
        given(usersRepository.findById(123456l)).willReturn(Optional.empty());

        Optional<FollowMapDTO> followMapDTO = followMapService.follow(follower.getId(), 123456l);

        assertEquals(Optional.empty(), followMapDTO);
    }

    @Test
    public void getFollowing_success() {
        User follower = UserSampleData.generateFollower();
        User targetUser = UserSampleData.generateTargetUser();

        FollowMap followMap = FollowMapSampleData.generateFollowMap();
        List<FollowMap> following = new ArrayList<>();
        following.add(followMap);

        given(usersRepository.findById(follower.getId())).willReturn(Optional.of(follower));
        given(followMapRepository.getFollowMapByFollower(follower)).willReturn(following);

        Optional<List<UserDTO>> userDTOs = followMapService.getFollowing(follower.getId());

        assertEquals(targetUser.getUserName(), userDTOs.get().get(0).getUserName());
    }

    @Test
    public void getFollowing_failure() {
        given(usersRepository.findById(123456l)).willReturn(Optional.empty());
        Optional<List<UserDTO>> userDTOs = followMapService.getFollowing(123456l);

        assertEquals(Optional.empty(), userDTOs);
    }

    @Test
    public void getFollowers_success() {
        User follower = UserSampleData.generateFollower();
        User targetUser = UserSampleData.generateTargetUser();

        FollowMap followMap = FollowMapSampleData.generateFollowMap();
        List<FollowMap> followers = new ArrayList<>();
        followers.add(followMap);

        given(usersRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        given(followMapRepository.getFollowMapByTargetUser(targetUser)).willReturn(followers);

        Optional<List<UserDTO>> userDTOs = followMapService.getFollowers(targetUser.getId());

        assertEquals(follower.getUserName(), userDTOs.get().get(0).getUserName());
    }

    @Test
    public void getFollowers_failure() {
        given(usersRepository.findById(123456l)).willReturn(Optional.empty());
        Optional<List<UserDTO>> userDTOs = followMapService.getFollowers(123456l);

        assertEquals(Optional.empty(), userDTOs);
    }

    @Test
    public void unfollow_success() {
        User follower = UserSampleData.generateFollower();
        User targetUser = UserSampleData.generateTargetUser();
        FollowMap followMap = FollowMapSampleData.generateFollowMap();

        given(usersRepository.findById(follower.getId())).willReturn(Optional.of(follower));
        given(usersRepository.findById(targetUser.getId())).willReturn(Optional.of(targetUser));
        given(followMapRepository.findByFollowerAndTargetUser(follower,targetUser)).willReturn(followMap);

        Optional<String> unfollowStatus = followMapService.unfollow(follower.getId(), targetUser.getId());

        assertEquals(follower.getUserName() + " unfollowed " + targetUser.getUserName(), unfollowStatus.get());
    }

    @Test
    public void unfollow_failure() {
        User follower = UserSampleData.generateFollower();
        given(usersRepository.findById(follower.getId())).willReturn(Optional.of(follower));
        given(usersRepository.findById(123456l)).willReturn(Optional.empty());

        Optional<String> unfollowStatus = followMapService.unfollow(follower.getId(), 123456l);

        assertEquals(Optional.empty(), unfollowStatus);
    }
}
