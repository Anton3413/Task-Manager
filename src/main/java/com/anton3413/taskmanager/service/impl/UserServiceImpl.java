package com.anton3413.taskmanager.service.impl;

import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.repository.UserRepository;
import com.anton3413.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       return userRepository.findUserByUsername(username)
               .map(user -> new org.springframework.security.core.userdetails.User(
                       user.getUsername(),
                       user.getPassword(),
                       AuthorityUtils.createAuthorityList("USER")
               )).orElseThrow(() ->new UsernameNotFoundException("There is no such User"));
    }

    @Override
    public boolean existsByUsername(String username) {
      return userRepository.existsUserByUsername(username);
    }
    public boolean existsByEmail(String email){
      return userRepository.existsByEmail(email);
    }

    @Transactional
   public User save(User user){
      return userRepository.save(user);
   }

    @Override
    public User findByUsername(String username) {
      return  userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not Found"));
    }
}
