package com.anton3413.taskmanager.controller;

import com.anton3413.taskmanager.mapper.UserMapper;
import com.anton3413.taskmanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = true)
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @MockitoBean
    private UserService userService;


    @Test
    void displayLoginPage_shouldReturnLoginPageWhenUserNotLoggedIn() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
    @Test
    void displayLoginPage_shouldRedirectToLogoutWhenUserAlreadyLoggedIn() throws Exception {
        Authentication auth = new UsernamePasswordAuthenticationToken("user",
                "pass", List.of());

        mockMvc.perform(get("/login").principal(auth))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logout"));
    }
}