package com.anton3413.taskmanager.mapper;

import com.anton3413.taskmanager.dto.user.CreateUserDto;
import com.anton3413.taskmanager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class UserMapperTest {
    private UserMapper userMapper;
    private static final String USERNAME = "Demo_title";
    private static final String EMAIL = "example@gmail.com";

    @BeforeEach
    void initUserMapper(){
        userMapper = new UserMapper();
    }

    @Test
    void fromCreateUserDtoToEntity(){
        CreateUserDto sourceObject = CreateUserDto.builder()
                .username(USERNAME).email(EMAIL)
                .build();

        User targetObject = userMapper.fromCreateUserDtoToEntity(sourceObject);

        assertEquals(sourceObject.getEmail(), targetObject.getEmail());
        assertEquals(sourceObject.getUsername(), targetObject.getUsername());
        assertThat(targetObject.getCreatedAt()).isCloseTo(LocalDateTime.now(),within(2, ChronoUnit.SECONDS));
    }
}
