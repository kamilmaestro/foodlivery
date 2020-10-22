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
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

import static com.kamilmarnik.foodlivery.utils.DateUtils.nowPlusMinutes;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

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
        .setExpiration(nowPlusMinutes(jwtConfig.getTokenExpirationAfterMinutes()))
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
