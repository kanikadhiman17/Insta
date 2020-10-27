package com.java.zolo.instagram.controller;

import com.java.zolo.instagram.domain.dto.comments.CommentsDTO;
import com.java.zolo.instagram.sampleData.CommentsSampleData;
import com.java.zolo.instagram.sampleData.TestUtils;
import com.java.zolo.instagram.service.comments.CommentsService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CommentsControllerTest {

    //@Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CommentsController commentsController;

    @Mock
    CommentsService commentsService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentsController).build();
    }

    @Test
    public void postCommentTest() throws Exception{
        CommentsDTO commentsDTO = CommentsSampleData.generateCommentsDTO();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/12345/posts/100/comments")
                .content(TestUtils.convertToJsonString(commentsDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void replyToACommentTest() throws Exception{
        CommentsDTO commentsDTO = CommentsSampleData.generateCommentsDTO();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/12345/comments/200")
                .content(TestUtils.convertToJsonString(commentsDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void getParentCommentsOnAPostTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/100/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void getRepliesOnACommentTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void updateCommentTest() throws Exception{
        CommentsDTO commentsDTO = CommentsSampleData.generateCommentsDTO();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/comments/200")
                .content(TestUtils.convertToJsonString(commentsDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void deleteCommentTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/comments/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

}
