package com.kamilmarnik.foodlivery.payment.domain

import com.kamilmarnik.foodlivery.infrastructure.PageInfo
import com.kamilmarnik.foodlivery.order.dto.AcceptedOrderDto
import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto
import com.kamilmarnik.foodlivery.order.dto.FinishedOrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto
import com.kamilmarnik.foodlivery.payment.dto.PaymentDto
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.utils.OrderTemplate
import spock.lang.Unroll

import java.time.Instant

class PaymentCreationSpec extends BasePaymentSpec {

  private static final Instant NOW = Instant.now()
  private static final double FOOD_PRICE = 10.0

  private OrderTemplate orderTemplate
  private FinishedOrderDto finishedOrder

  def setup() {
    given: "$JOHN is logged in"
      logInUser(JOHN)
    and: "there is $NOW"
      timeProvider.withFixedTime(NOW)
    and: "there is a $PIZZA_RESTAURANT with a $PIZZA in the menu"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: PIZZA_RESTAURANT.name))
      FoodDto food = supplierFacade.addFoodToSupplierMenu(newFood(name: PIZZA, supplierId: supplier.id, price: FOOD_PRICE))
    and: "$JOHN creates a proposal for the $PIZZA from the $PIZZA_RESTAURANT"
      orderFacade.createProposal(newProposal(supplierId: supplier.id, foodId: food.id, amountOfFood: 1, channelId: CHANNEL_ID))
    and: "$MARC creates a proposal for two $PIZZA's from the $PIZZA_RESTAURANT as well"
      logInUser(MARC)
      orderFacade.createProposal(newProposal(supplierId: supplier.id, foodId: food.id, amountOfFood: 2, channelId: CHANNEL_ID))
    and: "an order finished by $JOHN"
      logInUser(JOHN)
      AcceptedOrderDto acceptedOrder = orderFacade.becomePurchaser(newPurchaser(supplier.id, CHANNEL_ID))
      FinalizedOrderDto finalizedOrder = orderFacade.finalizeOrder(acceptedOrder.id)
      finishedOrder = orderFacade.finishOrder(finalizedOrder.id)
      orderTemplate = new OrderTemplate(finishedOrder)
  }

  @Unroll
  def "should create a new payment after finish of an order" () {
    given: "$user is logged in"
      logInUser(user)
    when: "order is finished"
      paymentFacade.onOrderFinished(orderTemplate.finished(NOW))
    then: "$user sees new payment"
      PaymentDto payment = paymentFacade.findUserCharges(PageInfo.DEFAULT).content
          .find({ p -> p.getPayerId() == user.userId })
      payment.purchaserId == purchaserId
      payment.payerId == user.userId
      payment.supplierId == finishedOrder.supplierId
      payment.channelId == finishedOrder.channelId
      payment.price == price
      UserOrderDto userOrder = finishedOrder.userOrders.find({ o -> o.getOrderedFor() == user.userId })
      payment.details.foodName == [userOrder.foodName]
      payment.details.foodAmount == [userOrder.foodAmount]
      payment.details.foodPrice == [userOrder.foodPrice]
    where:
      user  ||  purchaserId  || price
      JOHN  ||  JOHN.userId  || FOOD_PRICE
      MARC  ||  JOHN.userId  || FOOD_PRICE * 2
  }

  @Unroll
  def "user should see his money due" () {
    given: "order is finished"
      paymentFacade.onOrderFinished(orderTemplate.finished(NOW))
    and: "$user is logged in"
      logInUser(user)
    expect: "$user sees his money due"
      paymentFacade.findUserMoneyDue(PageInfo.DEFAULT).content.size() == dueSize
    where:
      user  ||  dueSize
      JOHN  ||  2
      MARC  ||  0
  }

  @Unroll
  def "user should see his charges" () {
    given: "order is finished"
      paymentFacade.onOrderFinished(orderTemplate.finished(NOW))
    and: "$user is logged in"
      logInUser(user)
    expect: "$user sees his charges"
      paymentFacade.findUserCharges(PageInfo.DEFAULT).content.price == toPay
    where:
      user  ||  toPay
      JOHN  ||  [FOOD_PRICE]
      MARC  ||  [FOOD_PRICE * 2]
  }

}
