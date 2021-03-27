package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.security.jwt.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
class ChannelConfiguration {

  ChannelFacade channelFacade(JwtConfig jwtConfig, SecretKey secretKey) {
    return channelFacade(new InMemoryChannelRepository(), new InMemoryChannelMemberRepository(), jwtConfig, secretKey);
  }

  @Bean
  ChannelFacade channelFacade(ChannelRepository channelRepository,
                              ChannelMemberRepository channelMemberRepository,
                              JwtConfig jwtConfig,
                              SecretKey secretKey) {
    return ChannelFacade.builder()
        .channelRepository(channelRepository)
        .channelMemberRepository(channelMemberRepository)
        .channelInvitation(new ChannelInvitation(jwtConfig, secretKey))
        .build();
  }

}
