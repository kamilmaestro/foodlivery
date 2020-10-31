package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.dto.ChannelDto;
import com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "channels")
class Channel {

  @Setter(value = AccessLevel.PACKAGE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Embedded
  @AttributeOverride(name = "name", column = @Column(name = "name"))
  ChannelName name;

  @Column(name = "uuid")
  String uuid;

  @Column(name = "created_by")
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
