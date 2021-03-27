package com.kamilmarnik.foodlivery.security;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class RegistrationRequest {

  String username;
  String password;
  String email;

}
