package com.anton3413.taskmanager.mapper;


import com.anton3413.taskmanager.dto.user.CreateUserDto;
import com.anton3413.taskmanager.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User fromCreateUserDtoToEntity(CreateUserDto createUserDto){
       return User.builder()
                .username(createUserDto.getUsername())
                .email(createUserDto.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
