package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.infrastructure.PageInfo
import com.kamilmarnik.foodlivery.order.dto.ProposalDto

import java.time.Instant
import java.time.temporal.ChronoUnit

class OrderExpirationSpec extends BaseOrderSpec {

  def setup() {
    given: "proposals expire after 3 hours"
      expirationConfig.setExpirationAfterMinutes(180)
  }

  def "proposals should be expired after specified amount of time" () {
    given: "there is a proposal for the $PIZZA_RESTAURANT"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
    and: "4 hours have passed"
      timeProvider.withFixedTime(Instant.now().plus(3, ChronoUnit.HOURS))
    when: "proposals expiration status is updated"
      orderFacade.updateExpiredProposals()
    then: "the proposal for the $PIZZA_RESTAURANT has expired"
      orderFacade.findChannelProposals(proposal.channelId, PageInfo.DEFAULT).content == []
  }

  def "proposals with expiration date after specified expiration date should not be expired" () {
    given: "there is a proposal for the $PIZZA_RESTAURANT"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
    when: "proposals expiration status is updated"
      orderFacade.updateExpiredProposals()
    then: "the proposal for the $PIZZA_RESTAURANT has not expired"
      orderFacade.findChannelProposals(proposal.channelId, PageInfo.DEFAULT).content.id == [proposal.id]
  }

}
