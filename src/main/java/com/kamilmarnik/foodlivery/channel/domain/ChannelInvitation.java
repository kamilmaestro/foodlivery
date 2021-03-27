package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.exception.InvalidInvitation;
import com.kamilmarnik.foodlivery.security.jwt.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.kamilmarnik.foodlivery.utils.DateUtils.currentDatePlusMinutes;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class ChannelInvitation {

  JwtConfig jwtConfig;
  SecretKey secretKey;

  String generate(Channel channel) {
    return Jwts.builder()
        .setSubject(channel.getUuid())
        .setIssuedAt(new Date())
        .setExpiration(currentDatePlusMinutes(jwtConfig.getTokenExpirationAfterMinutes()))
        .signWith(secretKey)
        .compact();
  }

  String getDecodedChannelUuidFrom(String invitation) {
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(invitation)
          .getBody();

      return claims.getSubject();
    } catch (Exception ex) {
      throw new InvalidInvitation("Invalid invitation token - can not join to any channel");
    }
  }

}
