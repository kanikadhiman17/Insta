package com.java.zolo.instagram.service.likes;

import com.java.zolo.instagram.domain.dto.likes.LikesDTO;

import java.util.List;
import java.util.Optional;

public interface LikesService {

    Optional<String> likeAPost(long userId, long postId);
    Optional<String> likeAComment(long userId, long commentId);
    Optional<List<LikesDTO>> fetchLikesOnAPost(long postId);
    Optional<List<LikesDTO>> fetchLikesOnAComment(long commentId);
    Optional<String> unlikeAPost(long userId, long postId);
    Optional<String> unlikeAComment(long userId, long commentId);
}
