package com.glos.api.authservice.controller;

import com.glos.api.authservice.dto.SignUpRequest;
import com.glos.api.authservice.mapper.SignUpRequestMapper;
import com.glos.api.authservice.util.security.JwtEntity;
import com.glos.api.authservice.util.security.JwtRequest;
import com.glos.api.authservice.util.security.JwtResponse;
import com.glos.api.authservice.util.security.SimpleAuthService;
import com.glos.api.entities.Roles;
import com.glos.api.entities.User;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @MockBean
    private SignUpRequestMapper signUpRequestMapper;
    @MockBean
    private SimpleAuthService simpleAuthService;

    @Test
    void registerUserNoCorrectDataTest() throws Exception {
        SignUpRequest request = new SignUpRequest();
        request.setUsername("user62");
        request.setPassword("Kkolya.com62");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setRoles(Collections.singletonList(Roles.ROLE_USER.asEntity()));

        JwtEntity jwtEntity = new JwtEntity(() -> user);
        JwtRequest jwtRequest = new JwtRequest();
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken("Test Token");

        when(signUpRequestMapper.toEntity(request)).thenReturn(user);
        when(simpleAuthService.authenticate(jwtRequest)).thenReturn(jwtResponse);

        ObjectMapper mapper = new ObjectMapper();

        assertThrows(AuthenticationException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    @WithMockUser(username = "user62", roles = {"ADMIN"})
    void registerAdminTest() throws Exception {
        SignUpRequest request = new SignUpRequest();
        User user = new User();
        Roles roles = Roles.ROLE_ADMIN;

        when(signUpRequestMapper.toEntity(request)).thenReturn(user);
        when(authService.create(user ,roles)).thenReturn("Admin created successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginTest() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("testUser");
        jwtRequest.setPassword("testPassword");

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken("testToken");

        when(simpleAuthService.authenticate(any(JwtRequest.class))).thenReturn(jwtResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jwtRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void validateTokenTest() throws Exception {
        String token = "testToken";
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken("testToken");

        when(simpleAuthService.validate(token)).thenReturn(jwtResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/validate")
                        .param("token", token))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(simpleAuthService, times(1)).validate(token);

    }

    @Test
    void refreshTest() throws Exception {
        String refreshRequestJson = "{ \"token\": \"your_refresh_token_here\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refreshRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void logoutTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
