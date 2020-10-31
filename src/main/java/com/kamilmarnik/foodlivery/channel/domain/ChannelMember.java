package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.dto.ChannelMemberDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "channel_members")
class ChannelMember {

  @Setter(value = AccessLevel.PACKAGE)
  @Getter(value = AccessLevel.PACKAGE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "channel_id")
  Long channelId;

  @Column(name = "member_id")
  Long memberId;

  @Column(name = "joined_at")
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
