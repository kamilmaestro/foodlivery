package com.kamilmarnik.foodlivery.order.domain


import com.kamilmarnik.foodlivery.order.dto.OrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto
import com.kamilmarnik.foodlivery.order.exception.OrderFinalizationForbidden

class OrderFinalizationSpec extends BaseOrderSpec {

  def setup() {
    given: "$JOHN is logged in"
      logInUser(JOHN)
    and: "there is a $KRAKOW channel"
      CHANNEL_ID = channelFacade.createChannel(KRAKOW.name).id
  }

  def "purchaser should be able to finalize an order" () {
    given: "there is an order for the $PIZZA_RESTAURANT"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
      OrderDto order = orderFacade.becomePurchaser(newPurchaser(proposal.supplierId, proposal.channelId))
    when: "$JOHN finalizes the order for the $PIZZA_RESTAURANT"
      OrderDto finalizedOrder = orderFacade.finalizeOrder(order.id)
    then: "order for the $PIZZA_RESTAURANT is finalized by $JOHN"
      finalizedOrder.id == order.id
      finalizedOrder.supplierId == order.supplierId
      finalizedOrder.channelId == order.channelId
      finalizedOrder.purchaserId == order.purchaserId && finalizedOrder.purchaserId == JOHN.userId
      finalizedOrder.createdAt != null
    and: "this order contains specified info about $JOHN`s order"
      UserOrderDto johnOrder = finalizedOrder.userOrders.first()
      johnOrder.orderUuid == order.uuid
      johnOrder.orderedFood.first().amountOfFood == proposal.food.first().amountOfFood
      johnOrder.orderedFor == proposal.createdBy && johnOrder.orderedFor == JOHN.userId
  }

  def "should not finalize an order when he is not a purchaser" () {
    given: "there is an order for the $PIZZA_RESTAURANT created by $JOHN"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
      OrderDto order = orderFacade.becomePurchaser(newPurchaser(proposal.supplierId, proposal.channelId))
    and: "$MARC is logged in"
      logInUser(MARC)
    when: "$MARC wants to finalize order for the $PIZZA_RESTAURANT"
      orderFacade.finalizeOrder(order.id)
    then: "$MARC can not finalize this order because he is not a purchaser"
      thrown(OrderFinalizationForbidden)
  }

}
