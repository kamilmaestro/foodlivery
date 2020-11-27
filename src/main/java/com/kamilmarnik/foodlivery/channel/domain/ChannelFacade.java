package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.dto.AddChannelDto;
import com.kamilmarnik.foodlivery.channel.dto.ChannelDto;
import com.kamilmarnik.foodlivery.channel.dto.ChannelMemberDto;
import com.kamilmarnik.foodlivery.channel.exception.ChannelNotFound;
import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChannelFacade {

  ChannelRepository channelRepository;
  ChannelMemberRepository channelMemberRepository;
  ChannelInvitation channelInvitation;

  public ChannelDto createChannel(String name) {
    final Channel channel = Channel.builder()
        .name(new ChannelName(name))
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

  public Page<ChannelDto> findChannelsByUserId(PageInfo pageInfo) {
    final Collection<Long> userChannelIds = channelMemberRepository.findChannelIdsAllByUserId(getLoggedUserId());
    return channelRepository.getByIdIn(userChannelIds, pageInfo.toPageRequest())
        .map(Channel::dto);
  }

  public Collection<ChannelMemberDto> findChannelMembers(long channelId) {
    return channelMemberRepository.findByChannelId(channelId).stream()
        .map(ChannelMember::dto)
        .collect(Collectors.toList());
  }

}
