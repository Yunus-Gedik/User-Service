package org.yunusgedik.user.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.yunusgedik.user.Helper.MockDataCreator;
import org.yunusgedik.user.Model.User.User;
import org.yunusgedik.user.Security.JwtAuthenticationFilter;
import org.yunusgedik.user.Security.JwtKeyProvider;
import org.yunusgedik.user.Security.JwtService;
import org.yunusgedik.user.Security.SecurityConfig;
import org.yunusgedik.user.Service.UserService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@Import({JwtService.class, JwtKeyProvider.class, JwtAuthenticationFilter.class, SecurityConfig.class})
public class UserTest {

    @MockitoBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "1", roles = {"ADMIN"})
    @DisplayName("GET /user/get/3 admin should get user success")
    void shouldGetUser() throws Exception {
        User sampleUser = MockDataCreator.createSampleUser();

        when(userService.get(anyLong())).thenReturn(sampleUser);

        mockMvc.perform(get("/user/get/3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(sampleUser.getId()))
            .andExpect(jsonPath("$.email").value(sampleUser.getEmail()));
    }
}