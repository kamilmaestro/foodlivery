package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.dto.AddChannelDto;
import com.kamilmarnik.foodlivery.channel.dto.ChannelDto;
import com.kamilmarnik.foodlivery.channel.dto.ChannelMemberDto;
import com.kamilmarnik.foodlivery.channel.exception.ChannelNotFound;
import lombok.Builder;

import java.util.UUID;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;

@Builder
public class ChannelFacade {

  ChannelRepository channelRepository;
  ChannelMemberRepository channelMemberRepository;
  ChannelInvitation channelInvitation;

  public ChannelDto createChannel(AddChannelDto addChannel) {
    final Channel channel = Channel.builder()
        .name(new ChannelName(addChannel.getName()))
        .uuid(UUID.randomUUID().toString())
        .createdBy(getLoggedUserId())
        .build();
    final Channel savedChannel = channelRepository.save(channel);
    final ChannelMember channelMember = savedChannel.join();
    channelMemberRepository.save(channelMember);

    return savedChannel.dto();
  }

  public String generateInvitation(long channelId) {
    final Channel channel = channelRepository.findById(channelId)
        .orElseThrow(() -> new ChannelNotFound("Channel not found with id: " + channelId));

    return channelInvitation.generate(channel);
  }

  public ChannelMemberDto joinChannel(String invitation) {
    final String channelUuid = channelInvitation.getDecodedChannelUuidFrom(invitation);
    final Channel channel = channelRepository.findByUuid(channelUuid)
        .orElseThrow(() -> new ChannelNotFound("Channel not found with uuid: " + channelUuid));
    final ChannelMember channelMember = channel.join();

    return channelMemberRepository.save(channelMember).dto();
  }

}
