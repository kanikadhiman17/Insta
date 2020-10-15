package com.java.zolo.instagram.repository;

import com.java.zolo.instagram.domain.model.FollowMap;
import com.java.zolo.instagram.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowMapRepository extends JpaRepository<FollowMap, Long> {
    List<FollowMap> getFollowMapByFollower(User user);
    List<FollowMap> getFollowMapByTargetUser(User user);
    FollowMap findByFollowerAndTargetUser(User follower, User targetUser);
}
