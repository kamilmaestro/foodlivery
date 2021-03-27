package com.kamilmarnik.foodlivery.channel.domain

import com.kamilmarnik.foodlivery.security.jwt.JwtConfig
import spock.lang.Specification

import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

import static com.kamilmarnik.foodlivery.utils.TextGenerator.randomText
import static io.jsonwebtoken.SignatureAlgorithm.HS256
import static javax.xml.bind.DatatypeConverter.parseBase64Binary

abstract class BaseChannelSpec extends Specification {

  JwtConfig jwtConfig = Mock(JwtConfig.class)
  String secretKeyValue = randomText(50)
  SecretKey secretKey = new SecretKeySpec(parseBase64Binary(secretKeyValue), HS256.getJcaName())
  ChannelFacade channelFacade = new ChannelConfiguration().channelFacade(jwtConfig, secretKey)

  def setup() {
    jwtConfig.getSecretKey() >> secretKeyValue
    jwtConfig.getTokenExpirationAfterMinutes() >> 10
  }

}
