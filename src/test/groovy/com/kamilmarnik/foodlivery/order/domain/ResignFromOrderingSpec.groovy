package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.infrastructure.PageInfo
import com.kamilmarnik.foodlivery.order.dto.OrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto

class ResignFromOrderingSpec extends BaseOrderSpec {

  def setup() {
    given: "$JOHN is logged in"
      logInUser(JOHN)
    and: "there is a $KRAKOW channel"
      CHANNEL_ID = channelFacade.createChannel(KRAKOW.name).id
  }

  def "should be able to resign from being a purchaser" () {
    given: "$JOHN is the purchaser for the $PIZZA_RESTAURANT"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
      OrderDto order = orderFacade.becomePurchaser(newPurchaser(proposal.supplierId, proposal.channelId))
    when: "$JOHN resigns from being a purchaser"
      orderFacade.resignFromPurchase(order.id)
    then: "user does not see this order"
      orderFacade.findOrdersInChannel(CHANNEL_ID, PageInfo.DEFAULT).content == []
  }

}
