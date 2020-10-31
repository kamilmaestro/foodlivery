package com.kamilmarnik.foodlivery.user.domain;

import com.kamilmarnik.foodlivery.security.RegistrationRequest;
import com.kamilmarnik.foodlivery.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFacade {

  PasswordEncoder passwordEncoder;
  UserRepository userRepository;

  public UserDto registerUser(RegistrationRequest registrationRequest) {
    User toSave = User.builder()
        .username(registrationRequest.getUsername())
        .password(registrationRequest.getPassword())
        .email(registrationRequest.getEmail())
        .role(User.UserRole.REGISTERED)
        .build();
    return userRepository.save(toSave).dto();
  }

}
