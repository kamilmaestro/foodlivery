package com.kamilmarnik.foodlivery.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamilmarnik.foodlivery.security.LoginRequest;
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
    String token = Jwts.builder()
        .setSubject(authResult.getName())
        .claim("authorities", authResult.getAuthorities())
        .setIssuedAt(new Date())
        .setExpiration(Date.from(LocalDateTime.now().plusHours(jwtConfig.getTokenExpirationAfterHours()).atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(secretKey)
        .compact();

    response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
  }

}
