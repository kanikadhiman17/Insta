package com.java.zolo.instagram.service;

import com.java.zolo.instagram.domain.dto.postImages.PostImagesDTO;
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
}
