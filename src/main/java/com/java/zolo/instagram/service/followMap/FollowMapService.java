package com.java.zolo.instagram.service.followMap;

import com.java.zolo.instagram.domain.dto.followMap.FollowMapDTO;
import com.java.zolo.instagram.domain.dto.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface FollowMapService {

    Optional<FollowMapDTO> follow(long userId, long targetId);

    Optional<List<UserDTO>> getFollowing(long userId);

    Optional<List<UserDTO>> getFollowers(long userId);

    Optional<String> unfollow(long userId, long targetId);
}
