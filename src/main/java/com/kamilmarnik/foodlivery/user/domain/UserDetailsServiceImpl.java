package com.kamilmarnik.foodlivery.user.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userRepository.findUserByUsername(userName)
        .orElseThrow(() -> new UsernameNotFoundException("Can not find user with such username: " + userName));

    return CustomUserDetails.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .role(user.getRole())
        .build();
  }

}
