package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.channel.dto.ChannelDto
import com.kamilmarnik.foodlivery.order.dto.AcceptedOrderDto
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.order.exception.CanNotBePurchaser
import com.kamilmarnik.foodlivery.order.exception.OrderForSupplierAlreadyExists
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto

class PurchaserSpec extends BaseOrderSpec {

  def "should be able to become a purchaser" () {
    given: "$JOHN creates a proposal with food from the $PIZZA_RESTAURANT"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
    when: "$JOHN applies himself as the purchaser for this supplier"
      AcceptedOrderDto order = orderFacade.becomePurchaser(newPurchaser(proposal.supplierId, proposal.channelId))
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
  }

  def "should not become a purchaser when has not previously created a proposal" () {
    given: "$JOHN creates a proposal with food from the $PIZZA_RESTAURANT"
      ProposalDto johnProposal = addProposal(PIZZA_RESTAURANT.name)
    and: "$MARC is logged in"
      logInUser(MARC)
    when: "$MARC wants to become a purchaser for orders connected with $PIZZA_RESTAURANT"
      orderFacade.becomePurchaser(newPurchaser(johnProposal.supplierId, johnProposal.channelId))
    then: "$MARC can not be a purchaser because he has not created a proposal"
      thrown(CanNotBePurchaser)
  }

  def "should not become a purchaser when has not previously created a proposal for a specified supplier" () {
    given: "there is a supplier: $KEBAB_RESTAURANT"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: KEBAB_RESTAURANT.name))
    and: "$JOHN adds a new proposal with food from $PIZZA_RESTAURANT"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
    when: "$JOHN adds wants to become a purchaser for orders connected with $KEBAB_RESTAURANT"
      orderFacade.becomePurchaser(newPurchaser(supplier.id, proposal.channelId))
    then: "$JOHN can not be a purchaser for $KEBAB_RESTAURANT when he has not created a proposal for $KEBAB_RESTAURANT"
      thrown(CanNotBePurchaser)
  }

  def "should convert proposals into orders when user applies as the purchaser for the specified supplier" () {
    given: "$KEVIN has created a proposal with food from the $KEBAB_RESTAURANT menu"
      logInUser(KEVIN)
      ProposalDto kevinProposal = addProposal(KEBAB_RESTAURANT.name)
    and: "$MARC and $JOHN have created proposals with food from the $PIZZA_RESTAURANT menu"
      logInUser(MARC)
      ProposalDto marcProposal = addProposal(PIZZA_RESTAURANT.name, kevinProposal.channelId)
      logInUser(JOHN)
      ProposalDto johnProposal = addProposal(marcProposal.supplierId, kevinProposal.channelId)
    when: "$JOHN applies himself as the purchaser"
      List<UserOrderDto> userOrders = orderFacade.becomePurchaser(newPurchaser(johnProposal.supplierId, johnProposal.channelId))
          .getUserOrders()
    then: "$JOHN and $MARC proposals become orders"
      userOrders.orderedFor.containsAll(marcProposal.createdBy, johnProposal.createdBy)
    and: "$KEVIN proposal is not still an order"
      !userOrders.orderedFor.contains(kevinProposal.createdBy)
  }

  def "should not create another order for the same supplier" () {
    given: "$JOHN has applied himself as the purchaser for the $PIZZA_RESTAURANT"
      ProposalDto johnProposal = addProposal(PIZZA_RESTAURANT.name)
      orderFacade.becomePurchaser(newPurchaser(johnProposal.supplierId, johnProposal.channelId))
    when: "$KEVIN wants to become a purchaser for the $PIZZA_RESTAURANT"
      orderFacade.becomePurchaser(newPurchaser(johnProposal.supplierId, johnProposal.channelId))
    then: "another order for the $PIZZA_RESTAURANT can not be created"
      thrown(OrderForSupplierAlreadyExists)
  }

  def "should be able to make another order for the same supplier in the another channel" () {
    given: "there is a $PIZZA_RESTAURANT"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: PIZZA_RESTAURANT.name))
      FoodDto food = supplierFacade.addFoodToSupplierMenu(newFood(supplierId: supplier.id))
    and: "there are: $KRAKOW and $WARSAW channels"
      ChannelDto krakow = channelFacade.createChannel(KRAKOW.name)
      ChannelDto warsaw = channelFacade.createChannel(WARSAW.name)
    and: "$JOHN has became a purchaser for the $PIZZA_RESTAURANT in the $KRAKOW channel"
      orderFacade.createProposal(newProposal(supplierId: supplier.id, channelId: krakow.id, foodId: food.id))
      orderFacade.becomePurchaser(newPurchaser(supplier.id, krakow.id))
    and: "$JOHN has created a proposal for the $PIZZA_RESTAURANT in the $WARSAW channel"
      orderFacade.createProposal(newProposal(supplierId: supplier.id, channelId: warsaw.id, foodId: food.id))
    expect: "$JOHN is able to become a purchaser for the $PIZZA_RESTAURANT in the $WARSAW channel"
      AcceptedOrderDto order = orderFacade.becomePurchaser(newPurchaser(supplier.id, warsaw.id))
      order.supplierId == supplier.id
      order.purchaserId == JOHN.userId
      order.channelId == warsaw.id
  }

}
