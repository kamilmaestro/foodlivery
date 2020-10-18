package com.kamilmarnik.foodlivery.channel.domain;

import com.kamilmarnik.foodlivery.channel.exception.InvalidChannelName;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class ChannelName {

  private static final int MIN_NAME_LENGTH = 4;
  public static final int MAX_NAME_LENGTH = 100;

  String name;

  ChannelName(String name) {
    this.name = Optional.ofNullable(name)
        .filter(value -> hasProperLength(value) && hasText(value))
        .orElseThrow(() -> new InvalidChannelName("Can not create a channel with name: " + name));
  }

  String getValue() {
    return this.name;
  }

  private boolean hasProperLength(String value) {
    return value.length() >= MIN_NAME_LENGTH && value.length() <= MAX_NAME_LENGTH;
  }

}