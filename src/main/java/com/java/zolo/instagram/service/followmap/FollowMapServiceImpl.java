package com.java.zolo.instagram.service.followmap;

import com.java.zolo.instagram.domain.dto.followMap.FollowMapDTO;
import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.domain.model.FollowMap;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.FollowMapRepository;
import com.java.zolo.instagram.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowMapServiceImpl implements FollowMapService{

    private final FollowMapRepository followMapRepository;
    private final UsersRepository usersRepository;
    ModelMapper modelMapper = new ModelMapper();


    public FollowMapServiceImpl(FollowMapRepository followMapRepository, UsersRepository usersRepository) {
        this.followMapRepository = followMapRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<FollowMapDTO> follow(long userId, long targetId) {
        Optional<User> follower = usersRepository.findById(userId);
        Optional<User> target = usersRepository.findById(targetId);
        if (target.isEmpty() || follower.isEmpty())
            return Optional.empty();
        /*modelMapper.typeMap(FollowMap.class, FollowMapDTO.class).addMapping(FollowMap::getFollower, FollowMapDTO::setFollower)
                .addMapping(FollowMap::getTargetUser, FollowMapDTO::setTargetUser);*/
        return Optional.of(modelMapper.map(followMapRepository.save(new FollowMap().setFollower(follower.get()).setTargetUser(target.get())), FollowMapDTO.class));
    }

    @Override
    public Optional<List<UserDTO>> getFollowing(long userId) {
        Optional<User> user = usersRepository.findById(userId);
        if(user.isEmpty())
            return Optional.empty();
        List<User> following = followMapRepository.getFollowMapByFollower(user.get())
                .stream().map(FollowMap::getTargetUser).collect(Collectors.toList());
        return Optional.of(following.stream().map(f -> modelMapper.map(f, UserDTO.class)).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<UserDTO>> getFollowers(long userId) {
        Optional<User> user = usersRepository.findById(userId);
        if(user.isEmpty())
            return Optional.empty();
        List<User> followers = followMapRepository.getFollowMapByTargetUser(user.get())
                .stream().map(FollowMap::getFollower).collect(Collectors.toList());
        return Optional.of(followers.stream().map(f -> modelMapper.map(f, UserDTO.class)).collect(Collectors.toList()));
    }

    @Override
    public Optional<String> unfollow(long userId, long targetId) {
        Optional<User> follower = usersRepository.findById(userId);
        Optional<User> target = usersRepository.findById(targetId);
        if (target.isEmpty() || follower.isEmpty())
            return Optional.empty();
        followMapRepository.delete(followMapRepository.findByFollowerAndTargetUser(follower.get(), target.get()));
        return Optional.of(follower.get().getUserName() + " unfollowed " + target.get().getUserName());
    }
}
