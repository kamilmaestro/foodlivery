package com.kamilmarnik.foodlivery.security;

import com.kamilmarnik.foodlivery.user.domain.UserFacade;
import com.kamilmarnik.foodlivery.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
class AuthController {

  UserFacade userFacade;

  @Autowired
  AuthController(@Autowired UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> registerUser(@RequestBody RegistrationRequest user) {
    UserDto registeredUser = userFacade.registerUser(user);

    return ResponseEntity.ok(registeredUser);
  }
}
