package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.security.jwt.JwtConfig;

import javax.crypto.SecretKey;

class ChannelConfiguration {

  ChannelFacade channelFacade(JwtConfig jwtConfig, SecretKey secretKey) {
    return channelFacade(new InMemoryChannelRepository(), new InMemoryChannelMemberRepository(), jwtConfig, secretKey);
  }

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
