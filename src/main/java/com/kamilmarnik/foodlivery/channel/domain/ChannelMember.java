package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.dto.ChannelMemberDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ChannelMember {

  @Setter(value = AccessLevel.PACKAGE)
  @Getter(value = AccessLevel.PACKAGE)
  Long id;
  Long channelId;
  Long memberId;
  LocalDateTime joinedAt;

  ChannelMember(long channelId, long memberId) {
    this.channelId = channelId;
    this.memberId = memberId;
    this.joinedAt = now();
  }

  public ChannelMemberDto dto() {
    return ChannelMemberDto.builder()
        .id(this.id)
        .channelId(this.channelId)
        .memberId(this.memberId)
        .joinedAt(this.joinedAt)
        .build();
  }

}
