package com.java.zolo.instagram.repository;

import com.java.zolo.instagram.domain.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Query(value = "SELECT * FROM likes WHERE target_type LIKE 'Post' AND fk_target_id=?1", nativeQuery = true)
    List<Likes> getAllByPosts(long postId);
    @Query(value = "SELECT * FROM likes WHERE target_type LIKE 'Comment' AND fk_target_id=?1", nativeQuery = true)
    List<Likes> getAllByComments(long commentId);
    @Query(value = "SELECT * FROM likes WHERE target_type LIKE 'Post' AND fk_user = ?1 AND fk_target_id=?2", nativeQuery = true)
    Optional<Likes> getByUserAndPost(long userId, long postId);
    @Query(value = "SELECT * FROM likes WHERE target_type LIKE 'Comment' AND fk_user = ?1 AND fk_target_id=?2", nativeQuery = true)
    Optional<Likes> getByUserAndComment(long userId, long postId);
}
