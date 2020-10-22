package com.java.zolo.instagram.service.postsImages;

import com.java.zolo.instagram.domain.dto.postImages.PostImagesDTO;
import com.java.zolo.instagram.domain.dto.postImages.PostUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface PostsImagesService {

    Optional<PostImagesDTO> uploadPostAndImages(PostImagesDTO postImagesDTO, long userId);
    Optional<List<PostImagesDTO>> fetchPostsFromUser(long userId);
    Optional<PostImagesDTO> fetchPostsFromId(long postId);
    Optional<PostImagesDTO> editPost(long postId, PostUpdateDTO postUpdateDTO);
    Optional<String> deletePost(long postId);
}
