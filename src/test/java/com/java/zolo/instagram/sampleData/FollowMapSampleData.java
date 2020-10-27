package com.java.zolo.instagram.sampleData;

import com.java.zolo.instagram.domain.model.FollowMap;
import com.java.zolo.instagram.domain.model.User;

public class FollowMapSampleData {

    public static FollowMap generateFollowMap() {
        User targetUser = UserSampleData.generateTargetUser();
        User follower = UserSampleData.generateFollower();

        FollowMap followMap = new  FollowMap();
        followMap.setTargetUser(targetUser);
        followMap.setFollower(follower);

        return followMap;
    }
}
