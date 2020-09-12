package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.supplier.exception.SupplierNotFound
import spock.lang.Specification

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

}
