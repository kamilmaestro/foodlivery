//package com.kamilmarnik.foodlivery.order.domain
//
//import com.kamilmarnik.foodlivery.order.dto.AcceptedOrderDto
//import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto
//import com.kamilmarnik.foodlivery.order.dto.ProposalDto
//import com.kamilmarnik.foodlivery.order.dto.UserOrderDto
//import com.kamilmarnik.foodlivery.user.dto.UserDto
//
//class DeleteUserOrderSpec extends BaseOrderSpec {
//
//  def "purchaser is able to delete an user order from the finalized order" () {
//    given: "there is an order for the $PIZZA_RESTAURANT with user orders added by: $JOHN and $MARC"
//      ProposalDto johnProposal = addProposal(PIZZA_RESTAURANT.name)
//      logInUser(MARC)
//      ProposalDto marcProposal = addProposal(johnProposal.supplierId)
//    and: "this order is finalized by $MARC who is a pruchaser"
//      AcceptedOrderDto order = orderFacade.becomePurchaser(marcProposal.supplierId, marcProposal.channelId)
//      FinalizedOrderDto finalizedOrder = orderFacade.finalizeOrder(order.id)
//    when: "$MARC removes $JOHN order"
//      FinalizedOrderDto withoutRemovedUserOrder = orderFacade
//          .removeUserOrder(getUserOrder(finalizedOrder, JOHN).id, finalizedOrder.id)
//    then: "order for the $PIZZA_RESTAURANT contains only $MARC user order"
//    withoutRemovedUserOrder.getUserOrders().id == [getUserOrder(finalizedOrder, MARC).id]
//  }
//
//  static UserOrderDto getUserOrder(FinalizedOrderDto finalizedOrder, UserDto user) {
//    return finalizedOrder.getUserOrders().stream()
//        .filter({userOrder -> userOrder.getOrderedFor() == user.userId })
//        .findFirst()
//        .orElse(null)
//  }
//
//}
