package com.kamilmarnik.foodlivery.samples

import com.kamilmarnik.foodlivery.user.dto.UserDto

trait SampleUsers {

  static final UserDto JOHN = createUserDto(11l, "John", "john@email.com")
  static final UserDto MARC = createUserDto(12l, "Marc", "marc@email.com")
  static final UserDto KEVIN = createUserDto(12l, "Kevin", "kevin@email.com")

  private static UserDto createUserDto(Long id, String name, String email) {
    UserDto.builder()
        .userId(id)
        .username(name)
        .email(email)
        .build()
  }

}