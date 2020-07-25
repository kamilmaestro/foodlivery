package com.kamilmarnik.foodlivery.infrastructure.authentication;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
class LoggedUserGetterCreator {

  @Bean
  public LoggedUserGetter createLoggedUserGetter() {
    return new LoggedUserGetter();
  }

}