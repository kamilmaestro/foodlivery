package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.dto.ChannelDto;
import com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
class Channel {

  @Setter(value = AccessLevel.PACKAGE)
  Long id;
  ChannelName name;
  String uuid;
  Long createdBy;

  ChannelDto dto() {
    return ChannelDto.builder()
        .id(this.id)
        .name(this.name.getValue())
        .createdBy(this.createdBy)
        .build();
  }

  ChannelMember join() {
    return new ChannelMember(this.id, getLoggedUserId());
  }

}
