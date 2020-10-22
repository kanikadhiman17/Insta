package com.java.zolo.instagram.sampleData;

import com.java.zolo.instagram.domain.dto.postImages.PostImagesDTO;
import com.java.zolo.instagram.domain.dto.postImages.PostUpdateDTO;
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
}
