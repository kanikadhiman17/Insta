package com.java.zolo.instagram.repository;

import com.java.zolo.instagram.domain.model.Comments;
import com.java.zolo.instagram.domain.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> getAllByPost(Posts post);
}
