package com.kamilmarnik.foodlivery.channel.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ChannelMemberDto {

  long id;
  long channelId;
  long memberId;
  LocalDateTime joinedAt;

}
