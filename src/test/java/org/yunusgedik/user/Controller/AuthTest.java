package org.yunusgedik.user.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.yunusgedik.user.Helper.MockDataCreator;
import org.yunusgedik.user.Model.User.UserDTO;
import org.yunusgedik.user.Service.AuthService;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AuthService authService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /auth/register success")
    void shouldRegisterSuccess() throws Exception{
        String mockToken = "sampleBearerToken";
        UserDTO userDTO = MockDataCreator.createSampleUserDTO();

        when(authService.register(any(UserDTO.class))).thenReturn(Map.of("token", mockToken));

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.token").value(mockToken));

    }

}