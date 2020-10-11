package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.infrastructure.PageInfo
import com.kamilmarnik.foodlivery.order.dto.OrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import org.springframework.data.domain.Page

class PurchaserSpec extends BaseOrderSpec {

  private PageInfo pageInfo = new PageInfo(0, 10)

  def "should be able to become a purchaser" () {
    given: "there is a supplier $PIZZA_RESTAURANT"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
    when: "$JOHN applies himself as the purchaser for this supplier"
      boolean result = orderFacade.becomePurchaser(supplier.id)
    then: "$JOHN is a purchaser for orders connected with $PIZZA_RESTAURANT"
      result
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
      Page<OrderDto> orders = orderFacade.findOrdersFromSupplier(johnProposal.supplierId, pageInfo)
      orders.content.id.containsAll(marcProposal.proposalId, johnProposal.proposalId)
    and: "$KEVIN proposal is not still an order"
      !orders.content.id.contains(kevinProposal.proposalId)
  }

}
