package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import spock.lang.Specification

class AddSupplierSpec extends Specification implements SampleSuppliers, SampleFood {

  private final SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()

  def "should add a new supplier" () {
    when: "wants to add a new supplier"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
    then: "there is a supplier"
      supplierFacade.getSupplier(supplier.id) == supplier
  }
}
