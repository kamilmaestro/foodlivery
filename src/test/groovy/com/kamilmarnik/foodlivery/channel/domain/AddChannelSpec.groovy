package com.kamilmarnik.foodlivery.channel.domain

import com.kamilmarnik.foodlivery.SecurityContextProvider
import com.kamilmarnik.foodlivery.channel.dto.ChannelDto
import com.kamilmarnik.foodlivery.channel.dto.ChannelMemberDto
import com.kamilmarnik.foodlivery.channel.exception.ChannelNotFound
import com.kamilmarnik.foodlivery.channel.exception.InvalidChannelName
import com.kamilmarnik.foodlivery.channel.exception.InvalidInvitation
import com.kamilmarnik.foodlivery.infrastructure.PageInfo
import com.kamilmarnik.foodlivery.samples.SampleChannels
import com.kamilmarnik.foodlivery.samples.SampleUsers
import com.kamilmarnik.foodlivery.security.jwt.JwtConfig
import spock.lang.Specification
import spock.lang.Unroll

import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

import static com.kamilmarnik.foodlivery.utils.TextGenerator.randomText
import static io.jsonwebtoken.SignatureAlgorithm.HS256
import static javax.xml.bind.DatatypeConverter.parseBase64Binary

class AddChannelSpec extends Specification implements SampleUsers, SampleChannels, SecurityContextProvider {

  JwtConfig jwtConfig = Mock(JwtConfig.class)
  String secretKeyValue = randomText(50)
  SecretKey secretKey = new SecretKeySpec(parseBase64Binary(secretKeyValue), HS256.getJcaName())
  ChannelFacade channelFacade = new ChannelConfiguration().channelFacade(jwtConfig, secretKey)

  def setup() {
    logInUser(JOHN)
    jwtConfig.getSecretKey() >> secretKeyValue
    jwtConfig.getTokenExpirationAfterMinutes() >> 10
  }

  def "should be able to create a new channel" () {
    when: "$JOHN creates new channel: $KRAKOW"
      ChannelDto channel = channelFacade.createChannel(KRAKOW.name)
    then: "$KRAKOW channel is created by $JOHN"
      channel.createdBy == JOHN.userId
      channel.name == KRAKOW.name
  }

  @Unroll
  def "should not be able to create a channel with name longer than 100 characters and shorter than 4" () {
    when: "$JOHN wants to create a new channel with name: $name"
      channelFacade.createChannel(name)
    then: "channel is not created"
      thrown(InvalidChannelName)
    where:
      name << ["", null, "123", "    ", randomText(101)]
  }

  def "should be able to join a channel" () {
    given: "there is a $KRAKOW channel created by $JOHN"
      ChannelDto krakowChannel = channelFacade.createChannel(KRAKOW.name)
    and: "$JOHN generates a invitation to the $KRAKOW channel"
      String invitation = channelFacade.generateInvitation(krakowChannel.id)
    and: "$MARC is logged in"
      logInUser(MARC)
    when: "$MARC uses the invitation to join the $KRAKOW channel"
      ChannelMemberDto channelMember = channelFacade.joinChannel(invitation)
    then: "$MARC is a member of the $KRAKOW channel"
      channelMember.channelId == krakowChannel.id
      channelMember.memberId == MARC.userId
      channelMember.joinedAt != null
  }

  def "should not join a non-existing channel" () {
    when: "$MARC wants to join a non-existing channel"
      channelFacade.joinChannel("fakeInvitation")
    then: "$MARC has not joined any channel"
      thrown(InvalidInvitation)
  }

  def "should not be able to join a channel twice" () {
    given: "there is a $KRAKOW channel created by $JOHN"
      ChannelDto krakowChannel = channelFacade.createChannel(KRAKOW.name)
      String invitation = channelFacade.generateInvitation(krakowChannel.id)
    and: "$MARC joins a $KRAKOW channel"
      logInUser(MARC)
      channelFacade.joinChannel(invitation)
    when: "$MARC wants to join a $KRAKOW channel once again"
      channelFacade.joinChannel(invitation)
    then: "$MARC still belongs to one channel: $KRAKOW"
      channelFacade.findChannelsByUserId(PageInfo.DEFAULT).content.id == [krakowChannel.id]
    and: "$KRAKOW contains two members: $JOHN and $MARC"
      channelFacade.findChannelMembers(krakowChannel.id).memberId.sort() == [JOHN.userId, MARC.userId].sort()
  }

}
