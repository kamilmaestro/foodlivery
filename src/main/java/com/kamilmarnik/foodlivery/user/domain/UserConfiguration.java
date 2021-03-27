package com.kamilmarnik.foodlivery.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserConfiguration {

  @Bean
  public UserFacade createUserFacade(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    return UserFacade.builder()
        .passwordEncoder(passwordEncoder)
        .userRepository(userRepository)
        .build();
  }

}
