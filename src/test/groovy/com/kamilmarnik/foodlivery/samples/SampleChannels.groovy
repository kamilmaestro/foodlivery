package com.kamilmarnik.foodlivery.samples

import com.kamilmarnik.foodlivery.channel.dto.AddChannelDto

trait SampleChannels {

  static final long FAKE_CHANNEL_ID = 0l

  AddChannelDto KRAKOW = AddChannelDto.builder().name("Krakow").build()
  AddChannelDto WARSAW = AddChannelDto.builder().name("Warsaw").build()

}