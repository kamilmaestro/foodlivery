package com.kamilmarnik.foodlivery.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamilmarnik.foodlivery.security.LoginRequest;
import com.kamilmarnik.foodlivery.security.LoginResponse;
import com.kamilmarnik.foodlivery.user.domain.CustomUserDetails;
import com.kamilmarnik.foodlivery.user.dto.UserDto;
import com.kamilmarnik.foodlivery.utils.DateUtils;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.kamilmarnik.foodlivery.utils.DateUtils.nowPlusMinutes;

public class JwtUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;

  public JwtUsernameAndPasswordAuthFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
    this.authenticationManager = authenticationManager;
    this.jwtConfig = jwtConfig;
    this.secretKey = secretKey;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          loginRequest.getUsername(), loginRequest.getPassword()
      );
      return authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    final long userId = ((CustomUserDetails) authResult.getPrincipal()).getUserId();
    String token = Jwts.builder()
        .setSubject(authResult.getName())
        .claim("userId", userId)
        .claim("authorities", authResult.getAuthorities())
        .setIssuedAt(new Date())
        .setExpiration(nowPlusMinutes(jwtConfig.getTokenExpirationAfterMinutes()))
        .signWith(secretKey)
        .compact();

    response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
    final LoginResponse user = new LoginResponse(userId, authResult.getName());
    String json = new ObjectMapper().writeValueAsString(user);
    response.getWriter().write(json);
    response.flushBuffer();
  }

}
