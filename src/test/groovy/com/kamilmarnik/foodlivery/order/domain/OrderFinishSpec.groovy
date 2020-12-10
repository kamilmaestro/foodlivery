package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto
import com.kamilmarnik.foodlivery.order.dto.FinishedOrderDto

class OrderFinishSpec extends BaseOrderSpec {

  def setup() {
    given: "$JOHN is logged in"
      logInUser(JOHN)
    and: "there is a $KRAKOW channel"
      CHANNEL_ID = channelFacade.createChannel(KRAKOW.name).id
  }

  def "should be able to finish an order" () {
    given: "there is an finalized order for the $PIZZA_RESTAURANT created by $JOHN"
      FinalizedOrderDto finalizedOrder = newFinalizedOrder(PIZZA_RESTAURANT.name)
    when: "$JOHN finish the order"
      FinishedOrderDto finishedOrder = orderFacade.finishOrder(finalizedOrder.id)
    then: "order for the $PIZZA_RESTAURANT is finished by $JOHN and can not be modified anymore"
      finishedOrder.id == finalizedOrder.id
      finishedOrder.supplierId == finalizedOrder.supplierId
      finishedOrder.channelId == finalizedOrder.channelId
      finishedOrder.purchaserId == finalizedOrder.purchaserId && finalizedOrder.purchaserId == JOHN.userId
      finishedOrder.createdAt != null
  }

}
