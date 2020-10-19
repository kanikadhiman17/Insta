package com.java.zolo.instagram.repository;

import com.java.zolo.instagram.domain.model.Comments;
import com.java.zolo.instagram.domain.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    @Query(value = "select * from comments where fk_post=?1 and fk_comment is null", nativeQuery = true)
    List<Comments> fetchAllByPostId(long postId);
    List<Comments> getAllByReply(Comments comments);
}
