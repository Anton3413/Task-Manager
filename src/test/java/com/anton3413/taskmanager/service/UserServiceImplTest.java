package com.anton3413.taskmanager.service;

import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.repository.UserRepository;
import com.anton3413.taskmanager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final String USERNAME = "john_doe";
    private static final String EMAIL = "example@gmail.com";
    private static final String PASSWORD = "qwerty123~";
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldReturnUserDetailsWhenUserExists(){

        User user = User.builder()
                .username(USERNAME).password(PASSWORD)
                .email(EMAIL)
                .build();

        when(userRepository.findUserByUsername(USERNAME)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(USERNAME);

        assertNotNull(userDetails);
        assertEquals(USERNAME, userDetails.getUsername());
        assertEquals(PASSWORD, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("USER")));
        verify(userRepository).findUserByUsername(USERNAME);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExists(){
        when(userRepository.findUserByUsername(USERNAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(USERNAME));
        verify(userRepository).findUserByUsername(USERNAME);
    }

    @Test
    void shouldReturnTrueWhenUserExists(){
        when(userRepository.existsUserByUsername(USERNAME)).thenReturn(true);

       boolean result = userService.existsByUsername(USERNAME);

       assertTrue(result);
       verify(userRepository).existsUserByUsername(USERNAME);
    }

    @Test
    void shouldReturnFalseWhenUserDoesntExist(){
        when(userRepository.existsUserByUsername(USERNAME)).thenReturn(false);

        boolean result = userService.existsByUsername(USERNAME);

        assertFalse(result);
        verify(userRepository).existsUserByUsername(USERNAME);
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenEmailExists() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);
        assertTrue(userService.existsByEmail(EMAIL));
        verify(userRepository).existsByEmail(EMAIL);
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenEmailDoesNotExist() {
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        assertFalse(userService.existsByEmail(EMAIL));
        verify(userRepository).existsByEmail(EMAIL);
    }

    @Test
    void saveMethodShouldReturnCreatedUser(){
        User userToSave = User.builder().username("new_user").build();
        User savedUser = User.builder().id(1L).username("new_user").build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.save(userToSave);

        assertNotNull(result.getId());
        verify(userRepository).save(userToSave);
    }

    @Test
    void returnUserWithoutException(){

        User user = User.builder()
                .id(1L)
                .username(USERNAME)
                .email(EMAIL)
                .build();

        when(userRepository.findUserByUsername(USERNAME)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.findByUsername(USERNAME));
        verify(userRepository).findUserByUsername(USERNAME);

    }

    @Test
    void throwsExceptionWhenUserNotFoundByUsername(){

        when(userRepository.findUserByUsername(USERNAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername(USERNAME));
        verify(userRepository).findUserByUsername(USERNAME);
    }
}
