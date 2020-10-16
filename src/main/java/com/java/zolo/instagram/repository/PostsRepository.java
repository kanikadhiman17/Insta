package com.java.zolo.instagram.repository;

import com.java.zolo.instagram.domain.model.Posts;
import com.java.zolo.instagram.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> getAllByUser(User user);
}
