package com.java.zolo.instagram.controller;

import com.java.zolo.instagram.domain.dto.postImages.PostImagesDTO;
import com.java.zolo.instagram.domain.dto.postImages.PostUpdateDTO;
import com.java.zolo.instagram.sampleData.PostsImagesSampleData;
import com.java.zolo.instagram.sampleData.TestUtils;
import com.java.zolo.instagram.service.postsImages.PostsImagesService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PostsImagesControllerTest {

    //@Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PostsImagesController postsImagesController;

    @Mock
    PostsImagesService postsImagesService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postsImagesController).build();
    }

    @Test
    public void uploadPostTest() throws Exception{
        PostImagesDTO postImagesDTO = PostsImagesSampleData.generatePostImagesDTO();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/12345/posts")
                .content(TestUtils.convertToJsonString(postImagesDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void getPostsFromUserIdTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/12345/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void getPostsFromPostIdTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void editPostTest() throws Exception{
        PostUpdateDTO postUpdateDTO = PostsImagesSampleData.generatePostUpdateDTO();
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/posts/1")
                .content(TestUtils.convertToJsonString(postUpdateDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void deletePostTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

}
