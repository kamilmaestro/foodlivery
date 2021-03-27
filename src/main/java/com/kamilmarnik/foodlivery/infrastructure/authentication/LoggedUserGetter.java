package com.kamilmarnik.foodlivery.infrastructure.authentication;

import com.kamilmarnik.foodlivery.user.UserNotFound;
import com.kamilmarnik.foodlivery.user.domain.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class LoggedUserGetter {

  private LoggedUserGetter() {
    throw new IllegalStateException("LoggedUserGetter is na utility class an can not be instantiated");
  }

  public static String getLoggedUsername() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getName)
        .orElseThrow(() -> new UsernameNotFoundException("Can not get currently logged in user"));
  }

  public static Long getLoggedUserId() {
    return getLoggedInUser().getUserId();
  }

  public static boolean isAdmin() {
    return getLoggedInUser().isAdmin();
  }

  private static CustomUserDetails getLoggedInUser() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(authentication -> (CustomUserDetails) authentication.getPrincipal())
        .orElseThrow(() -> new UserNotFound("Can not get currently logged in user"));
  }

}
