package com.kamilmarnik.foodlivery.security.jwt;

import com.google.common.net.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
  private String secretKey;
  private String tokenPrefix;
  private int tokenExpirationAfterHours;

  public JwtConfig() {
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getTokenPrefix() {
    return tokenPrefix;
  }

  public void setTokenPrefix(String tokenPrefix) {
    this.tokenPrefix = tokenPrefix;
  }

  public int getTokenExpirationAfterHours() {
    return tokenExpirationAfterHours;
  }

  public void setTokenExpirationAfterHours(int tokenExpirationAfterHours) {
    this.tokenExpirationAfterHours = tokenExpirationAfterHours;
  }

  public String getAuthorizationHeader() {
    return HttpHeaders.AUTHORIZATION;
  }

}
