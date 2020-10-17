package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.infrastructure.PageInfo
import com.kamilmarnik.foodlivery.order.dto.OrderDto
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.order.exception.CanNotBePurchaser
import com.kamilmarnik.foodlivery.order.exception.OrderForSupplierAlreadyExists
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import org.springframework.data.domain.Page

class PurchaserSpec extends BaseOrderSpec {

  def "should be able to become a purchaser" () {
    given: "$JOHN creates a proposal with food from the $PIZZA_RESTAURANT"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
    when: "$JOHN applies himself as the purchaser for this supplier"
      OrderDto order = orderFacade.becomePurchaser(proposal.supplierId)
    then: "$JOHN is a purchaser for orders connected with $PIZZA_RESTAURANT"
      order.purchaserId == JOHN.userId
      order.supplierId == proposal.supplierId
      order.createdAt != null
      order.uuid != null
    and: "this order contains specified info about $JOHN`s order"
      UserOrderDto johnOrder = order.getUserOrders().first()
      johnOrder.orderUuid == order.uuid
      johnOrder.foodId == proposal.foodId
      johnOrder.foodAmount == proposal.foodAmount
      johnOrder.orderedFor == JOHN.userId
      johnOrder.createdAt != null
  }

  def "should not become a purchaser when has not previously created a proposal" () {
    given: "$JOHN creates a proposal with food from the $PIZZA_RESTAURANT"
      ProposalDto johnProposal = addProposal(PIZZA_RESTAURANT.name)
    and: "$MARC is logged in"
      logInUser(MARC)
    when: "$MARC wants to become a purchaser for orders connected with $PIZZA_RESTAURANT"
      orderFacade.becomePurchaser(johnProposal.supplierId)
    then: "$MARC can not be a purchaser because he has not created a proposal"
      thrown(CanNotBePurchaser)
  }

  def "should not become a purchaser when has not previously created a proposal for a specified supplier" () {
    given: "there is a supplier: $KEBAB_RESTAURANT"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: KEBAB_RESTAURANT.name))
    and: "$JOHN adds a new proposal with food from $PIZZA_RESTAURANT"
      addProposal(PIZZA_RESTAURANT.name)
    when: "$JOHN adds wants to become a purchaser for orders connected with $KEBAB_RESTAURANT"
      orderFacade.becomePurchaser(supplier.id)
    then: "$JOHN can not be a purchaser for $KEBAB_RESTAURANT when he has not created a proposal for $KEBAB_RESTAURANT"
      thrown(CanNotBePurchaser)
  }

  def "should convert proposals into orders when user applies as the purchaser for the specified supplier" () {
    given: "$KEVIN has created a proposal with food from the $KEBAB_RESTAURANT menu"
      ProposalDto kevinProposal = addProposal(KEBAB_RESTAURANT.name, KEVIN)
    and: "$MARC and $JOHN have created a proposal with food from the $PIZZA_RESTAURANT menu"
      ProposalDto marcProposal = addProposal(PIZZA_RESTAURANT.name, MARC)
      ProposalDto johnProposal = addProposal(marcProposal.supplierId, JOHN)
    when: "$JOHN applies himself as the purchaser"
      List<UserOrderDto> userOrders = orderFacade.becomePurchaser(johnProposal.supplierId).getUserOrders()
    then: "$JOHN and $MARC proposals become orders"
      userOrders.orderedFor.containsAll(marcProposal.createdBy, johnProposal.createdBy)
    and: "$KEVIN proposal is not still an order"
      !userOrders.orderedFor.contains(kevinProposal.createdBy)
  }

  def "should not create another order for the same supplier" () {
    given: "$JOHN has applied himself as the purchaser for the $PIZZA_RESTAURANT"
      ProposalDto johnProposal = addProposal(PIZZA_RESTAURANT.name)
      orderFacade.becomePurchaser(johnProposal.supplierId)
    and: "$KEVIN creates a proposal for the $PIZZA_RESTAURANT as well"
      ProposalDto kevinProposal = addProposal(johnProposal.supplierId, KEVIN)
    when: "$KEVIN wants to become a purchaser for the $PIZZA_RESTAURANT"
      orderFacade.becomePurchaser(kevinProposal.supplierId)
    then: "another order for the $PIZZA_RESTAURANT can not be created"
      thrown(OrderForSupplierAlreadyExists)
  }

}
