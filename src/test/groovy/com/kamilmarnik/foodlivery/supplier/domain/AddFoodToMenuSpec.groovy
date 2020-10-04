package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.supplier.exception.IncorrectAmountOfFood
import com.kamilmarnik.foodlivery.supplier.exception.SupplierNotFound
import spock.lang.Specification
import spock.lang.Unroll

class AddFoodToMenuSpec extends Specification implements SampleSuppliers, SampleFood {

  private SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()

  def "should be able to add food to the menu" () {
    given: "there is a supplier $PIZZA_RESTAURANT"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: PIZZA_RESTAURANT.name))
    when: "adds a food to the menu of $PIZZA_RESTAURANT"
      FoodDto addedFood = supplierFacade.addFoodToSupplierMenu(newFood(supplier.id))
    then: "menu of $PIZZA_RESTAURANT contains this food"
      supplierFacade.getSupplierMenu(addedFood.supplierId).getMenu()
          .contains(addedFood)
  }

  def "should not add food to a non-existing supplier" () {
    when: "adds a food to the menu"
      supplierFacade.addFoodToSupplierMenu(newFood(FAKE_SUPPLIER_ID))
    then: "menu of this supplier contains this food"
      thrown(SupplierNotFound)
  }

  @Unroll
  def "should not be able to add a food with wrong amount" () {
    given: "there is a supplier $PIZZA_RESTAURANT"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: PIZZA_RESTAURANT.name))
    when: "adds a food with amount: $amount to the menu of $PIZZA_RESTAURANT"
      supplierFacade.addFoodToSupplierMenu(newFood(supplier.id, amount))
    then: "this food is not added"
      thrown(IncorrectAmountOfFood)
    where:
      amount << [0, null, -1]
  }

}
