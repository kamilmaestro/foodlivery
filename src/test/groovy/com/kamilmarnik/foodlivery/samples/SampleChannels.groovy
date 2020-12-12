package com.kamilmarnik.foodlivery.samples

import com.kamilmarnik.foodlivery.channel.dto.AddChannelDto

trait SampleChannels {

  AddChannelDto KRAKOW = AddChannelDto.builder().name("Krakow").build()
  AddChannelDto WARSAW = AddChannelDto.builder().name("Warsaw").build()

}