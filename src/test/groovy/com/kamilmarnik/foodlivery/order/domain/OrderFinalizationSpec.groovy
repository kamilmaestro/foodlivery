package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto
import com.kamilmarnik.foodlivery.order.dto.OrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto

class OrderFinalizationSpec extends BaseOrderSpec {

  def "purchaser should be able to finalize an order" () {
    given: "there is an order for the $PIZZA_RESTAURANT"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
      OrderDto order = orderFacade.becomePurchaser(proposal.supplierId, proposal.channelId)
    when: "$JOHN finalizes the order for the $PIZZA_RESTAURANT"
      FinalizedOrderDto finalizedOrder = orderFacade.finalizeOrder(order.id)
    then: "order for the $PIZZA_RESTAURANT is finalized by $JOHN"
      finalizedOrder.id == order.id
      finalizedOrder.supplierId == order.supplierId
      finalizedOrder.channelId == order.channelId
      finalizedOrder.purchaserId == order.purchaserId && finalizedOrder.purchaserId == JOHN.userId
      finalizedOrder.finalizedAt != null
    and: "this order contains specified info about $JOHN`s order"
      UserOrderDto johnOrder = order.getUserOrders().first()
      johnOrder.orderUuid == order.uuid
      johnOrder.foodId == proposal.foodId
      johnOrder.foodAmount == proposal.foodAmount
      johnOrder.orderedFor == proposal.createdBy && johnOrder.orderedFor == JOHN.userId
  }

}
