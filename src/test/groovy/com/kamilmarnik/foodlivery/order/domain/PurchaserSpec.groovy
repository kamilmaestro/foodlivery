package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.infrastructure.PageInfo
import com.kamilmarnik.foodlivery.order.dto.OrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.order.exception.CanNotBePurchaser
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import org.springframework.data.domain.Page

class PurchaserSpec extends BaseOrderSpec {

  def "should be able to become a purchaser" () {
    given: "$JOHN creates a proposal with food from the $PIZZA_RESTAURANT"
      ProposalDto proposal = addProposal(PIZZA_RESTAURANT.name)
    when: "$JOHN applies himself as the purchaser for this supplier"
      orderFacade.becomePurchaser(proposal.supplierId)
    then: "$JOHN is a purchaser for orders connected with $PIZZA_RESTAURANT"
      OrderDto order = orderFacade.findOrdersFromSupplier(proposal.supplierId, PageInfo.DEFAULT).content.first()
      order.purchaserId == JOHN.userId
      order.createdBy == JOHN.userId
      order.createdAt != null
      order.supplierId == proposal.supplierId
      order.foodId == proposal.foodId
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
      logInUser(KEVIN)
      ProposalDto kevinProposal = addProposal(KEBAB_RESTAURANT.name)
    and: "$MARC has created a proposal with food from the $PIZZA_RESTAURANT menu"
      logInUser(MARC)
      ProposalDto marcProposal = addProposal(PIZZA_RESTAURANT.name)
    and: "$JOHN also has created a proposal with food from the $PIZZA_RESTAURANT menu"
      logInUser(JOHN)
      ProposalDto johnProposal = addProposal(marcProposal.supplierId)
    when: "$JOHN applies himself as the purchaser"
      orderFacade.becomePurchaser(johnProposal.supplierId)
    then: "$JOHN and $MARC proposals become orders"
      Page<OrderDto> orders = orderFacade.findOrdersFromSupplier(johnProposal.supplierId, PageInfo.DEFAULT)
      orders.content.id.containsAll(marcProposal.proposalId, johnProposal.proposalId)
    and: "$KEVIN proposal is not still an order"
      !orders.content.id.contains(kevinProposal.proposalId)
  }

}
