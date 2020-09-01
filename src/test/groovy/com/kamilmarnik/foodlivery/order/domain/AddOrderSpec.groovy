package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.samples.SampleUsers
import com.kamilmarnik.foodlivery.supplier.domain.SampleFood
import com.kamilmarnik.foodlivery.supplier.domain.SampleSuppliers
import com.kamilmarnik.foodlivery.supplier.domain.SupplierConfiguration
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.supplier.exception.SupplierNotFound
import spock.lang.Specification

class AddOrderSpec extends Specification implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood {

  private LoggedUserGetter loggedUserGetter = Mock(LoggedUserGetter.class)
  private SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()
  private OrderFacade orderFacade = new OrderConfiguration().orderFacade(loggedUserGetter, supplierFacade)

  def setup() {
    loggedUserGetter.getLoggedUserId() >> JOHN.userId
  }

  def "should create a new proposal" () {
    given: "there is a supplier"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
    and: "there is a food added to supplier"
      FoodDto addedFood = supplierFacade.addFoodToSupplierMenu(newFood(supplier.id))
    when: "creates a new proposal"
      ProposalDto proposal = orderFacade.createProposal(newProposal(supplier.id, addedFood.id))
    then: "proposal is added"
      proposal.supplierId == supplier.id
      proposal.userId == JOHN.userId
      proposal.createdAt != null
      proposal.food.id == addedFood.id
  }

  def "should not be able to create a new proposal with a non-existing supplier" () {
    when: "wants to create a new proposal"
      orderFacade.createProposal(newProposal(FAKE_SUPPLIER_ID, 0l))
    then: "proposal is not added due to wrong supplier"
      thrown(SupplierNotFound)
  }

}
