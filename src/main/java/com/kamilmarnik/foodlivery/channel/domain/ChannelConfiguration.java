package com.kamilmarnik.foodlivery.channel.domain;

class ChannelConfiguration {

  ChannelFacade channelFacade() {
    return channelFacade(new InMemoryChannelRepository());
  }

  ChannelFacade channelFacade(ChannelRepository channelRepository) {
    return ChannelFacade.builder()
        .channelRepository(channelRepository)
        .build();
  }

}
