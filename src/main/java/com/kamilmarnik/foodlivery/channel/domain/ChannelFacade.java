package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.dto.AddChannelDto;
import com.kamilmarnik.foodlivery.channel.dto.ChannelDto;
import lombok.Builder;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;

@Builder
public class ChannelFacade {

  ChannelRepository channelRepository;

  public ChannelDto createChannel(AddChannelDto addChannel) {
    final Channel channel = Channel.builder()
        .name(new ChannelName(addChannel.getName()))
        .createdBy(getLoggedUserId())
        .build();
    return channelRepository.save(channel).dto();
  }

}
