package com.anton3413.taskmanager.service;

import com.anton3413.taskmanager.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    boolean existsByUsername(String username);

    User save(User user);

    User findByUsername(String username);

}
