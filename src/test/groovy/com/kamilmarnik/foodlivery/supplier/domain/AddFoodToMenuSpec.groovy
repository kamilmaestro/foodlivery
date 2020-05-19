package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import spock.lang.Specification

class AddFoodToMenuSpec extends Specification implements SampleSuppliers, SampleFood {

  private final SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()

  def "should be able to add a food to the menu" () {
    given: "there is a supplier"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
    when: "adds a food to the menu"
      FoodDto addedFood = supplierFacade.addFoodToSupplierMenu(newFood(supplier.id))
    then: "menu of this supplier contains this food"
      supplierFacade.getSupplierMenu(addedFood.supplierId).getMenu()
          .contains(addedFood)
  }

}
