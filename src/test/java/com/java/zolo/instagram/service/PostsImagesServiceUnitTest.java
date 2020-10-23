package com.java.zolo.instagram.service;

import com.java.zolo.instagram.domain.dto.postImages.PostImagesDTO;
import com.java.zolo.instagram.domain.dto.postImages.PostUpdateDTO;
import com.java.zolo.instagram.domain.model.Images;
import com.java.zolo.instagram.domain.model.Posts;
import com.java.zolo.instagram.domain.model.User;
import com.java.zolo.instagram.repository.ImagesRepository;
import com.java.zolo.instagram.repository.PostsRepository;
import com.java.zolo.instagram.repository.UsersRepository;
import com.java.zolo.instagram.sampleData.PostsImagesSampleData;
import com.java.zolo.instagram.sampleData.UserSampleData;
import com.java.zolo.instagram.service.postsImages.PostsImagesServiceImpl;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class PostsImagesServiceUnitTest {

    @InjectMocks
    PostsImagesServiceImpl postsImagesService;

    @Mock
    PostsRepository postsRepository;

    @Mock
    UsersRepository usersRepository;

    @Mock
    ImagesRepository imagesRepository;

    @Test
    public void uploadPostAndImages_success() {
        PostImagesDTO postImagesDTO = PostsImagesSampleData.generatePostImagesDTO();
        User user = UserSampleData.generateUser();
        Posts post = PostsImagesSampleData.generatePost();
        Images image1 = PostsImagesSampleData.generateImage1();
        Images image2 = PostsImagesSampleData.generateImage2();

        given(usersRepository.findById(12345l)).willReturn(Optional.of(user));
        given(postsRepository.save(new Posts().setUser(user).setCaption(postImagesDTO.getCaption()).setLocation(postImagesDTO.getLocation()))).willReturn(post);
        given(imagesRepository.save(new Images().setPost(post).setImageUrl(postImagesDTO.getImageURLs().get(0)))).willReturn(image1);
        given(imagesRepository.save(new Images().setPost(post).setImageUrl(postImagesDTO.getImageURLs().get(1)))).willReturn(image2);

        Optional<PostImagesDTO> actualPostImagesDTO = postsImagesService.uploadPostAndImages(postImagesDTO, 12345l);

        assertEquals(postImagesDTO, actualPostImagesDTO.get());
        assertEquals(postImagesDTO.getImageURLs(), actualPostImagesDTO.get().getImageURLs());
    }

    @Test
    public void fetchPostFromUser_success() {
        User user = UserSampleData.generateUser();

        Posts post = PostsImagesSampleData.generatePost();
        List<Posts> posts = new ArrayList<>();
        posts.add(post);

        List<Images> images = new ArrayList<>();
        Images image1 = PostsImagesSampleData.generateImage1();
        Images image2 = PostsImagesSampleData.generateImage2();
        images.add(image1);
        images.add(image2);

        given(usersRepository.findById(12345l)).willReturn(Optional.of(user));
        given(postsRepository.getAllByUser(user)).willReturn(posts);
        given(imagesRepository.getAllByPost(post)).willReturn(images);

        Optional<List<PostImagesDTO>> actualPostImagesDTOList = postsImagesService.fetchPostsFromUser(12345l);

        assertEquals(post.getCaption(), actualPostImagesDTOList.get().get(0).getCaption());
        assertEquals(images.get(0).getImageUrl(), actualPostImagesDTOList.get().get(0).getImageURLs().get(0));
    }

    @Test
    public void fetchPostFromUser_failure() {
        given(usersRepository.findById(123456l)).willReturn(Optional.empty());
        Optional<List<PostImagesDTO>> actualPostImagesDTOList = postsImagesService.fetchPostsFromUser(123456l);
        assertEquals(Optional.empty(), actualPostImagesDTOList);
    }

    @Test
    public void fetchPostFromId_success() {
        Posts post = PostsImagesSampleData.generatePost();

        List<Images> images = new ArrayList<>();
        Images image1 = PostsImagesSampleData.generateImage1();
        Images image2 = PostsImagesSampleData.generateImage2();
        images.add(image1);
        images.add(image2);

        given(postsRepository.findById(100l)).willReturn(Optional.of(post));
        given(imagesRepository.getAllByPost(post)).willReturn(images);

        Optional<PostImagesDTO> actualPostImagesDTO = postsImagesService.fetchPostsFromId(100l);

        assertEquals(post.getCaption(), actualPostImagesDTO.get().getCaption());
        assertEquals(images.get(0).getImageUrl(), actualPostImagesDTO.get().getImageURLs().get(0));

    }

    @Test
    public void fetchPostFromId_failure() {
        given(postsRepository.findById(101l)).willReturn(Optional.empty());
        Optional<PostImagesDTO> actualPostImagesDTO = postsImagesService.fetchPostsFromId(100l);
        assertEquals(Optional.empty(), actualPostImagesDTO);
    }

    @Test
    public void editPost_success() {
        PostUpdateDTO postUpdateDTO = PostsImagesSampleData.generatePostUpdateDTO();
        Posts post = PostsImagesSampleData.generatePost();

        given(postsRepository.findById(100l)).willReturn(Optional.of(post));
        given(postsRepository.save(post)).willReturn(post);

        Optional<PostImagesDTO> actualPostImagesDTO = postsImagesService.editPost( 100l, postUpdateDTO);
        assertEquals(postUpdateDTO.getCaption(), actualPostImagesDTO.get().getCaption());
    }

    @Test
    public void editPost_failure() {
        PostUpdateDTO postUpdateDTO = PostsImagesSampleData.generatePostUpdateDTO();
        given(postsRepository.findById(101l)).willReturn(Optional.empty());

        Optional<PostImagesDTO> actualPostImagesDTO = postsImagesService.editPost( 101l, postUpdateDTO);
        assertEquals(Optional.empty(), actualPostImagesDTO);
    }
    @Test

    public void deletePost_success() {
        Posts post = PostsImagesSampleData.generatePost();
        given(postsRepository.findById(100l)).willReturn(Optional.of(post));
        Optional<String> deleteStatus = postsImagesService.deletePost( 100l);
        assertEquals("Post with id "+100l+" deleted!", deleteStatus.get());
    }

    @Test
    public void deletePost_failure() {
        given(postsRepository.findById(101l)).willReturn(Optional.empty());
        Optional<String> deleteStatus = postsImagesService.deletePost( 101l);
        assertEquals(Optional.empty(), deleteStatus);
    }
}
