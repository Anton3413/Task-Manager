package com.anton3413.taskmanager.controller;

import com.anton3413.taskmanager.mapper.UserMapper;
import com.anton3413.taskmanager.model.User;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    final Authentication auth = new UsernamePasswordAuthenticationToken(
            "user", "pass", List.of());


    @Test
    void displayLoginPage_shouldReturnLoginPageWhenUserNotLoggedIn() throws Exception {

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
    @Test
    void displayLoginPage_shouldRedirectToLogoutWhenUserAlreadyLoggedIn() throws Exception {
        mockMvc.perform(get("/login")
                        .principal(auth))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logout"));
    }

    @Test
    void displayRegistrationPage_shouldRedirectToLogoutWhenUserAlreadyLoggedIn() throws Exception {
        mockMvc.perform(get("/registration")
                        .principal(auth))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logout"));
    }

    @Test
    void displayRegistrationPage_shouldReturnRegistrationPageWhenUerNotLoggedIn() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(model().attributeExists("userDto"))
                .andExpect(view().name("registration"));
    }

    @Test
    void createNewUser_shouldCreateAndSaveNewUserWithoutValidationErrors() throws Exception {
        final String encodedPassword = "qdfgbn!!@@g3545tfgwre";
        final User newUser = User.builder()
                .email("example@gmail.com")
                .createdAt(LocalDateTime.now())
                .username("example123")
                .password("qwerty123!")
                .build();

        when(userMapper.fromCreateUserDtoToEntity(any())).thenReturn(newUser);
        when(passwordEncoder.encode(any())).thenReturn("qdfgbn!!@@g3545tfgwre");
        when(userService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/registration")
                        .with(csrf())
                        .param("username","example123")
                        .param("email", "example@gmail.com")
                        .param("password", "qwerty123!")
                        .param("confirmPassword", "qwerty123!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attributeExists("message", "messageType"));

        assertEquals(encodedPassword, newUser.getPassword());

        verify(userMapper).fromCreateUserDtoToEntity(any());
        verify(passwordEncoder).encode(any());
        verify(userService).save(any());
    }

    /*void createNewUser_shouldReturnToPreviousPageWithValidationErrorMessages() throws Exception {
        mockMvc.perform(post("/registration")
                .with(csrf())
                        .param("username","example123")
                        .param("email", "example@gmail.com")
                        .param("password", "qwerty123!")
                        .param("confirmPassword", "qwerty123!12345"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
    }*/

    @Test
    void displayLogoutPage_shouldReturnLogoutPageWhenUserLoggedIn() throws Exception {
        mockMvc.perform(get("/logout")
                .principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("logout"));
    }

    @Test
    void displayLogoutPage_shouldRedirectToLoginPageWhenUserNotLoggedIn() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}