package com.kamilmarnik.foodlivery.user.domain;

import com.kamilmarnik.foodlivery.user.dto.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class User {

  enum UserRole {
    ADMIN,
    REGISTERED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String username;
  String password;
  String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_status")
  @Builder.Default
  UserRole role = UserRole.REGISTERED;

  UserDto dto() {
    return UserDto.builder()
        .userId(this.id)
        .username(this.username)
        .email(this.email)
        .build();
  }

}
