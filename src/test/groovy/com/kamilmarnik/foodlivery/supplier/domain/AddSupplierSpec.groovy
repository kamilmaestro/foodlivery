package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.supplier.exception.InvalidSupplierData
import spock.lang.Specification
import spock.lang.Unroll

class AddSupplierSpec extends Specification implements SampleSuppliers, SampleFood {

  private final SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()

  def "should add a new supplier" () {
    when: "wants to add a new supplier"
      SupplierDto supplier = supplierFacade.addSupplier(newSupplier())
    then: "there is a supplier"
      supplierFacade.getSupplierDto(supplier.id) == supplier
  }

  @Unroll
  def "should not add a supplier with an invalid name" () {
    when: "wants to add a new supplier"
      supplierFacade.addSupplier(newSupplier(name: data))
    then: "supplier is not added due to wrong name"
      thrown(exception)
    where:
      data  ||  exception
      null  ||  InvalidSupplierData
      ""    ||  InvalidSupplierData
      "  "  ||  InvalidSupplierData
  }

  @Unroll
  def "should not add a supplier with an invalid phone number" () {
    when: "wants to add a new supplier"
      supplierFacade.addSupplier(newSupplier(phoneNumber: data))
    then: "supplier is not added due to wrong phone number"
      thrown(exception)
    where:
      data  ||  exception
      null  ||  InvalidSupplierData
      ""    ||  InvalidSupplierData
      "  "  ||  InvalidSupplierData
  }

}
