package com.kamilmarnik.foodlivery.samples

import com.kamilmarnik.foodlivery.channel.dto.AddChannelDto

trait SampleChannels {

  static final long FAKE_CHANNEL_ID = 0l

  AddChannelDto KRAKOW = AddChannelDto.builder().name("Krakow").build()
  AddChannelDto WARSAW = AddChannelDto.builder().name("Warsaw").build()

  static AddChannelDto newChannel(Map<String, Object> properties = [:]) {
    properties = NEW_FOOD_DEFAULT_VALUES + properties

    return AddChannelDto.builder()
        .name(properties.name as String)
        .build()
  }

  private static final Map NEW_FOOD_DEFAULT_VALUES = [
      "name" : "Channel"
  ]

}