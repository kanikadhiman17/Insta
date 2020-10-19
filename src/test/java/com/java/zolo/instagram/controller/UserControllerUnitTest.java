package com.java.zolo.instagram.controller;

import com.java.zolo.instagram.domain.dto.user.UserDTO;
import com.java.zolo.instagram.sampleData.TestUtils;
import com.java.zolo.instagram.sampleData.UserSampleData;
import com.java.zolo.instagram.service.user.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerUnitTest {

    //@Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    UserService userService;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void createUserTest() throws Exception{
        UserDTO userDTO = UserSampleData.generateUserDTO();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .content(TestUtils.convertToJsonString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                //.andExpect(jsonPath("$.data.userName", Matchers.is("Test")))
                //.andExpect(jsonPath("$.*", Matchers.hasSize(2)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateUserTest() throws Exception{
        UserDTO userDTO = UserSampleData.generateUserDTO();
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/100")
                .content(TestUtils.convertToJsonString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", Matchers.is("SUCCESS")))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
