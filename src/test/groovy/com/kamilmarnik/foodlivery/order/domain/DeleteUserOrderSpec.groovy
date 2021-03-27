package com.kamilmarnik.foodlivery.order.domain


import com.kamilmarnik.foodlivery.order.dto.OrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto
import com.kamilmarnik.foodlivery.order.exception.UserOrderRemovalForbidden
import com.kamilmarnik.foodlivery.user.dto.UserDto

class DeleteUserOrderSpec extends BaseOrderSpec {

  def setup() {
    given: "$JOHN is logged in"
      logInUser(JOHN)
    and: "there is a $KRAKOW channel"
      CHANNEL_ID = channelFacade.createChannel(KRAKOW.name).id
  }

  def "purchaser is able to delete an user order from the accepted order" () {
    given: "there is an order for the $PIZZA_RESTAURANT with user orders added by: $JOHN and $MARC"
      ProposalDto johnProposal = addProposal(PIZZA_RESTAURANT.name)
      logInUser(MARC)
      ProposalDto marcProposal = addProposal(johnProposal.supplierId)
    and: "$MARC is a purchaser"
      OrderDto order = orderFacade.becomePurchaser(newPurchaser(marcProposal.supplierId, marcProposal.channelId))
    when: "$MARC removes $JOHN order"
      OrderDto withoutRemovedUserOrder = orderFacade
        .removeUserOrder(getUserOrder(order, JOHN).id, order.id)
    then: "order for the $PIZZA_RESTAURANT contains only $MARC user order"
      withoutRemovedUserOrder.getUserOrders().orderedFor == [MARC.userId]
  }

  def "purchaser is able to delete an user order from the finalized order" () {
    given: "there is an order for the $PIZZA_RESTAURANT with user orders added by: $JOHN and $MARC"
      ProposalDto johnProposal = addProposal(PIZZA_RESTAURANT.name)
      logInUser(MARC)
      ProposalDto marcProposal = addProposal(johnProposal.supplierId)
    and: "this order is finalized by $MARC who is a pruchaser"
      OrderDto order = orderFacade.becomePurchaser(newPurchaser(marcProposal.supplierId, marcProposal.channelId))
      OrderDto finalizedOrder = orderFacade.finalizeOrder(order.id)
    when: "$MARC removes $JOHN order"
      OrderDto withoutRemovedUserOrder = orderFacade
          .removeUserOrder(getUserOrder(finalizedOrder, JOHN).id, finalizedOrder.id)
    then: "order for the $PIZZA_RESTAURANT contains only $MARC user order"
      withoutRemovedUserOrder.getUserOrders().id == [getUserOrder(finalizedOrder, MARC).id]
  }

  def "user is not able to delete his user order from the finalized order" () {
    given: "there is an order for the $PIZZA_RESTAURANT with user orders added by: $JOHN and $MARC"
      ProposalDto johnProposal = addProposal(PIZZA_RESTAURANT.name)
      logInUser(MARC)
      ProposalDto marcProposal = addProposal(johnProposal.supplierId)
    and: "this order is finalized by $MARC who is a pruchaser"
      OrderDto order = orderFacade.becomePurchaser(newPurchaser(marcProposal.supplierId, marcProposal.channelId))
      OrderDto finalizedOrder = orderFacade.finalizeOrder(order.id)
    and: "$JOHN is logged in"
      logInUser(JOHN)
    when: "$JOHN wants to remove his user order from the order"
      orderFacade.removeUserOrder(getUserOrder(finalizedOrder, JOHN).id, finalizedOrder.id)
    then: "$JOHN can not remove his user order because order is finalized"
      thrown(UserOrderRemovalForbidden)
  }

  static UserOrderDto getUserOrder(OrderDto order, UserDto user) {
    return order.getUserOrders().stream()
        .filter({userOrder -> userOrder.getOrderedFor() == user.userId })
        .findFirst()
        .orElse(null)
  }

}
