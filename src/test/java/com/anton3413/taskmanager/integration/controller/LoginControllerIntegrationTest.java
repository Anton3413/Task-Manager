package com.anton3413.taskmanager.integration.controller;

import com.anton3413.taskmanager.integration.BaseIT;
import com.anton3413.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@AutoConfigureMockMvc
public class LoginControllerIntegrationTest extends BaseIT {

    private static final String USERNAME = "user_1234";
    private static final String EMAIL = "example@gmail.com";
    private static final String PASSWORD = "qwerty12345!";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void createNewUser_shouldSaveNewUserWithoutErrors() throws Exception{

        mockMvc.perform(post("/registration")
                .with(csrf())
                .param("username", USERNAME)
                .param("email", EMAIL)
                .param("password", PASSWORD)
                .param("confirmPassword", PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attributeCount(2));

      String encodedPassword = userRepository.findUserByUsername(USERNAME).get().getPassword();

      assertTrue(passwordEncoder.matches(PASSWORD, encodedPassword));
    }
}
