package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.supplier.exception.FoodNotFound
import com.kamilmarnik.foodlivery.supplier.exception.IncorrectAmountOfFood
import com.kamilmarnik.foodlivery.supplier.exception.SupplierNotFound
import spock.lang.Unroll

class CreateProposalSpec extends BaseOrderSpec {

  def "should create a new proposal" () {
    given: "there is a supplier $KEBAB_RESTAURANT"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: KEBAB_RESTAURANT.name))
    and: "there is a food added to $KEBAB_RESTAURANT"
      FoodDto addedFood = supplierFacade.addFoodToSupplierMenu(newFood(supplierId: supplier.id))
    when: "$JOHN creates a new proposal with food from $KEBAB_RESTAURANT"
      ProposalDto proposal = orderFacade.createProposal(newProposal(supplierId: supplier.id, foodId: addedFood.id))
    then: "proposal created by $JOHN is added"
      proposal.supplierId == supplier.id
      proposal.createdBy == JOHN.userId
      proposal.createdAt != null
      proposal.expirationDate != null
      proposal.foodId == addedFood.id
  }

  @Unroll
  def "should not be able to create a proposal with amount of food not being a natural number" () {
    given: "there is food"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
      FoodDto food = supplierFacade.addFoodToSupplierMenu(newFood(supplierId: supplier.id))
    when: "$JOHN wants to create a proposal with wrong amount of food equals: $amount"
      orderFacade.createProposal(newProposal(amountOfFood: amount, supplierId: supplier.id, foodId: food.id))
    then: "proposal is not created"
      thrown(IncorrectAmountOfFood)
    where:
      amount << [0, -1, null]
  }

  def "should not be able to create a new proposal with a non-existing supplier" () {
    given: "there is food"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
      FoodDto food = supplierFacade.addFoodToSupplierMenu(newFood(supplierId: supplier.id))
    when: "wants to create a new proposal"
      orderFacade.createProposal(newProposal(supplierId: FAKE_SUPPLIER_ID, foodId: food.id))
    then: "proposal is not added due to wrong supplier"
      thrown(SupplierNotFound)
  }

  def "should not create a new proposal with a non-existing food" () {
    given: "there is a supplier"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
    when: "wants to create a new proposal"
      orderFacade.createProposal(newProposal(foodId: FAKE_FOOD_ID, supplierId: supplier.id))
    then: "proposal can not be created without a chosen food"
      thrown(FoodNotFound)
  }

}
