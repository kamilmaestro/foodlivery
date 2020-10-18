package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.dto.ChannelDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
class Channel {

  @Setter(value = AccessLevel.PACKAGE)
  @Getter(value = AccessLevel.PACKAGE)
  Long id;
  ChannelName name;
  Long createdBy;

  ChannelDto dto() {
    return ChannelDto.builder()
        .id(this.id)
        .name(this.name.getValue())
        .createdBy(this.createdBy)
        .build();
  }

}
