package com.java.zolo.instagram.sampleData;

import com.java.zolo.instagram.domain.dto.postImages.PostImagesDTO;
import com.java.zolo.instagram.domain.dto.postImages.PostUpdateDTO;
import com.java.zolo.instagram.domain.model.Images;
import com.java.zolo.instagram.domain.model.Posts;
import com.java.zolo.instagram.domain.model.User;
import java.util.ArrayList;

public class PostsImagesSampleData {

    public static PostImagesDTO generatePostImagesDTO() {
        PostImagesDTO postImagesDTO = new PostImagesDTO();
        postImagesDTO.setLocation("Test Location");
        postImagesDTO.setCaption("Test Caption");
        postImagesDTO.setImageURLs(new ArrayList<>() {
            {
                add("https://www.test1.com/test1");
                add("https://www.test2.com/test2");
            }
        });
        return postImagesDTO;
    }

    public static PostUpdateDTO generatePostUpdateDTO() {
        PostUpdateDTO postUpdateDTO = new PostUpdateDTO();
        postUpdateDTO.setLocation("Test Location");
        postUpdateDTO.setCaption("Test Caption");
        return postUpdateDTO;
    }

    public static Posts generatePost() {
        Posts post = new Posts();
        User user = UserSampleData.generateUser();
        post.setId(100l);
        post.setUser(user);
        post.setLocation("Test Location");
        post.setCaption("Test Caption");
        return post;
    }

    public static Images generateImage1() {
        Posts post = PostsImagesSampleData.generatePost();
        Images image1 = new Images();
        image1.setId(1001l);
        image1.setPost(post);
        image1.setImageUrl("https://www.test1.com/test1");
        return image1;
    }

    public static Images generateImage2() {
        Posts post = PostsImagesSampleData.generatePost();
        Images image2 = new Images();
        image2.setId(1002l);
        image2.setPost(post);
        image2.setImageUrl("https://www.test2.com/test2");
        return image2;
    }
}
