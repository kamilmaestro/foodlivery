package com.kamilmarnik.foodlivery.payment.domain

import com.kamilmarnik.foodlivery.infrastructure.PageInfo
import com.kamilmarnik.foodlivery.order.dto.AcceptedOrderDto
import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto
import com.kamilmarnik.foodlivery.order.dto.FinishedOrderDto
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto
import com.kamilmarnik.foodlivery.payment.dto.PaymentDto
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.utils.OrderTemplate
import spock.lang.Unroll

import java.time.Instant

import static com.kamilmarnik.foodlivery.payment.dto.PaymentStatusDto.PAID_BY_PAYER
import static com.kamilmarnik.foodlivery.payment.dto.PaymentStatusDto.TO_PAY

class PaymentSpec extends BasePaymentSpec {

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

  def "should create a new payment after finish of an order" () {
    when: "order is finished by $JOHN"
      paymentFacade.onOrderFinished(orderTemplate.finished(NOW))
    then: "$MARC sees new payment"
      logInUser(MARC)
      PaymentDto payment = paymentFacade.findUserCharges(PageInfo.DEFAULT).content.first()
      payment.purchaserId == JOHN.userId
      payment.payerId == MARC.userId
      payment.supplierId == finishedOrder.supplierId
      payment.channelId == finishedOrder.channelId
      payment.price == FOOD_PRICE * 2
      payment.createdAt == NOW
      UserOrderDto marcOrder = finishedOrder.userOrders.find({ o -> o.getOrderedFor() == MARC.userId })
      payment.details.foodName == [marcOrder.foodName]
      payment.details.foodAmount == [marcOrder.foodAmount]
      payment.details.foodPrice == [marcOrder.foodPrice]
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
      JOHN  ||  1
      MARC  ||  0
  }

  @Unroll
  def "user should see his charges" () {
    given: "order is finished"
      paymentFacade.onOrderFinished(orderTemplate.finished(NOW))
    and: "$user is logged in"
      logInUser(user)
    expect: "$user sees his charges"
      List<PaymentDto> charges = paymentFacade.findUserCharges(PageInfo.DEFAULT).content
      charges.price == toPay
      charges.status == status
    where:
      user  ||  toPay             ||  status
      JOHN  ||  []                ||  []
      MARC  ||  [FOOD_PRICE * 2]  ||  [TO_PAY]
  }

  def "payment is paid off only when purchaser marks it as paid off" () {
    given: "order is finished"
      paymentFacade.onOrderFinished(orderTemplate.finished(NOW))
    and: "$MARC is logged in"
      logInUser(MARC)
      PaymentDto marcPayment = paymentFacade.findUserCharges(PageInfo.DEFAULT).content.first()
    and: "$MARC pays off his charges"
      paymentFacade.marksAsPaidOff(marcPayment.id)
    and: "$JOHN is logged in"
      logInUser(JOHN)
    when: "$JOHN marks $MARC's payment as paid off"
      paymentFacade.marksAsPaidOff(marcPayment.id)
    then: "$JOHN has no longer any due"
      paymentFacade.findUserMoneyDue(PageInfo.DEFAULT).isEmpty()
    and: "$MARC has no longer any charges"
      logInUser(MARC)
      paymentFacade.findUserCharges(PageInfo.DEFAULT).isEmpty()
  }

  def "should have still charges when payment was not marked as paid off by the purchaser" () {
    given: "order is finished"
      paymentFacade.onOrderFinished(orderTemplate.finished(NOW))
    and: "$MARC is logged in"
      logInUser(MARC)
      PaymentDto marcPayment = paymentFacade.findUserCharges(PageInfo.DEFAULT).content.first()
    when: "$MARC pays off his charges"
      paymentFacade.marksAsPaidOff(marcPayment.id)
    then: "$MARC still has a payment to be paid off"
      paymentFacade.findUserCharges(PageInfo.DEFAULT).content.status == [PAID_BY_PAYER]
  }

  def "should not see any charges when is a purchaser as well" () {
    when: "$JOHN checks his charges"
      List<PaymentDto> charges = paymentFacade.findUserCharges(PageInfo.DEFAULT).content
    then: "he has nothing to be paid off"
      charges.isEmpty()
  }

}
