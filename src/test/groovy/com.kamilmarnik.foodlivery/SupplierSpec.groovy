package com.kamilmarnik.foodlivery

import spock.lang.Specification

class SupplierSpec extends Specification {

  SupplierFacade supplierFacade = new SupplierFacadeCreator().supplierFacade(new InMemorySupplierRepository())

  def "should add a new supplier" () {
    when: "wants to add a new supplier"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier("Eggs7"))
    then: "there is a supplier"
      SupplierDto foundSupplier = supplierFacade.getSupplier(supplier.id)
      foundSupplier == supplier
  }

  AddSupplierDto newSupplier(String name) {
    AddSupplierDto.builder()
        .name(name)
        .build()
  }
}
