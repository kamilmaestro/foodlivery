package com.kamilmarnik.foodlivery.channel.domain

import com.kamilmarnik.foodlivery.SecurityContextProvider
import com.kamilmarnik.foodlivery.channel.dto.ChannelDto
import com.kamilmarnik.foodlivery.channel.exception.InvalidChannelName
import com.kamilmarnik.foodlivery.samples.SampleChannels
import com.kamilmarnik.foodlivery.samples.SampleUsers
import spock.lang.Specification
import spock.lang.Unroll

import static com.kamilmarnik.foodlivery.utils.TextGenerator.randomText

class AddChannelSpec extends Specification implements SampleUsers, SampleChannels, SecurityContextProvider {

  ChannelFacade channelFacade = new ChannelConfiguration().channelFacade()

  def setup() {
    logInUser(JOHN)
  }

  def "should be able to create a new channel" () {
    when: "$JOHN creates new channel: $KRAKOW"
      ChannelDto channel = channelFacade.createChannel(newChannel(name: KRAKOW.name))
    then: "$KRAKOW channel is created by $JOHN"
      channel.createdBy == JOHN.userId
      channel.name == KRAKOW.name
  }

  @Unroll
  def "should not be able to create a channel with name longer than 100 characters and shorter than 4" () {
    when: "$JOHN wants to create a new channel with name: $name"
      channelFacade.createChannel(newChannel(name: name))
    then: "channel is not created"
      thrown(InvalidChannelName)
    where:
      name << ["", null, "123", "    ", randomText(101)]
  }

}
