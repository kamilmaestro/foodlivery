package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.SecurityContextProvider
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.samples.SampleUsers
import com.kamilmarnik.foodlivery.supplier.domain.SampleFood
import com.kamilmarnik.foodlivery.supplier.domain.SampleSuppliers
import com.kamilmarnik.foodlivery.supplier.domain.SupplierConfiguration
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.supplier.exception.IncorrectAmountOfFood
import com.kamilmarnik.foodlivery.supplier.exception.SupplierNotFound
import spock.lang.Specification
import spock.lang.Unroll

class CreateProposalSpec extends Specification implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SecurityContextProvider {

  private SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()
  private OrderFacade orderFacade = new OrderConfiguration().orderFacade(supplierFacade)

  def setup() {
    createContext(JOHN)
  }

  def "should create a new proposal" () {
    given: "there is a supplier $KEBAB_RESTAURANT"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: KEBAB_RESTAURANT.name))
    and: "there is a food added to $KEBAB_RESTAURANT"
      FoodDto addedFood = supplierFacade.addFoodToSupplierMenu(newFood(supplier.id))
    when: "$JOHN creates a new proposal with food from $KEBAB_RESTAURANT"
      ProposalDto proposal = orderFacade.createProposal(newProposal(supplierId: supplier.id, foodId: addedFood.id))
    then: "proposal created by $JOHN is added"
      proposal.supplierId == supplier.id
      proposal.userId == JOHN.userId
      proposal.createdAt != null
      proposal.foodId == addedFood.id
  }

  @Unroll
  def "should not be able to create a proposal with amount of food not being a natural number" () {
    given: "there is a food"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
      FoodDto food = supplierFacade.addFoodToSupplierMenu(newFood(supplier.id))
    when: "$JOHN wants to create a proposal with wrong amount of food equals: $amount"
      orderFacade.createProposal(newProposal(amountOfFood: amount, supplierId: supplier.id, foodId: food.id))
    then: "proposal is not created"
      thrown(IncorrectAmountOfFood)
    where:
      amount << [0, -1, null]
  }

  def "should not be able to create a new proposal with a non-existing supplier" () {
    when: "wants to create a new proposal"
      orderFacade.createProposal(newProposal(supplierId: FAKE_SUPPLIER_ID))
    then: "proposal is not added due to wrong supplier"
      thrown(SupplierNotFound)
  }

  //nie powinien dodac wiecej jedzenia niz supplier posiada

}
