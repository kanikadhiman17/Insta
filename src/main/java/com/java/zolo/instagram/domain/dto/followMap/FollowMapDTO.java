package com.java.zolo.instagram.domain.dto.followMap;

import com.java.zolo.instagram.domain.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowMapDTO {
    private UserDTO targetUser;
    private UserDTO follower;
}
