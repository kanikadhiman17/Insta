package com.java.zolo.instagram.controller;

import com.java.zolo.instagram.service.followMap.FollowMapService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class FollowMapControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FollowMapController followMapController;

    @Mock
    FollowMapService followMapService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(followMapController).build();
    }

    @Test
    public void followEventTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/12345/follow/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void getFollowingTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/12345/follows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void getFollowersTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/12345/followed-by"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

    @Test
    public void unfollowEventTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/12345/follow/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andReturn();
    }

}
