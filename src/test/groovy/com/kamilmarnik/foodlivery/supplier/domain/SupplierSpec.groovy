package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.AddFoodToMenuDto
import com.kamilmarnik.foodlivery.supplier.dto.AddSupplierDto
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierMenuDto
import com.kamilmarnik.foodlivery.supplier.exception.SupplierNotFound
import spock.lang.Specification

class SupplierSpec extends Specification {

  SupplierFacade supplierFacade = new SupplierFacadeCreator()
      .supplierFacade(new InMemorySupplierRepository(), new InMemoryFoodRepository())

  def "should add a new supplier" () {
    when: "wants to add a new supplier"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
    then: "there is a supplier"
      SupplierDto foundSupplier = supplierFacade.getSupplier(supplier.id)
      foundSupplier == supplier
  }

  def "should not get not existing supplier" () {
    when: "wants to get not existing supplier"
      supplierFacade.getSupplier(0L)
    then: "proper exception is thrown"
      thrown(SupplierNotFound)
  }

  def "should be able to add a food to the menu" () {
    given: "there is a supplier"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
    when: "adds a food to the menu"
      FoodDto addedFood = supplierFacade.addFoodToSupplierMenu(newFood(supplier.id))
    then: "menu of this supplier contains this food"
      SupplierMenuDto supplierMenu = supplierFacade.getSupplierMenu(addedFood.supplierId)
      supplierMenu.getMenu().contains(addedFood)
  }

  AddSupplierDto newSupplier(String name) {
    AddSupplierDto.builder()
        .name(name)
        .build()
  }

  AddFoodToMenuDto newFood(long supplierId) {
    AddFoodToMenuDto.builder()
        .name("Food")
        .supplierId(supplierId)
        .build()
  }
}
