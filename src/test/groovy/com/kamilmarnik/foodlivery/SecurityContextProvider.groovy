package com.kamilmarnik.foodlivery

import com.kamilmarnik.foodlivery.user.domain.CustomUserDetails
import com.kamilmarnik.foodlivery.user.dto.UserDto
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

trait SecurityContextProvider {

  void logInUser(UserDto user) {
    CustomUserDetails authenticatedUser = CustomUserDetails.builder()
        .userId(user.userId)
        .username(user.username)
        .build()
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(authenticatedUser, null)
    )
  }

}
