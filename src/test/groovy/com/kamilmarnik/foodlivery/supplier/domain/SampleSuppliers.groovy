package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.AddSupplierDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto

trait SampleSuppliers {

  long PIZZA_RESTAURANT_ID = 111L
  long KEBAB_RESTAURANT_ID = 112L
  long APPLE_RESTAURANT_ID = 113L

  SupplierDto PIZZA_RESTAURANT = SupplierDto.builder()
      .id(PIZZA_RESTAURANT_ID)
      .name("Pizza Restaurant")
      .build()

  SupplierDto KEBAB_RESTAURANT = SupplierDto.builder()
      .id(KEBAB_RESTAURANT_ID)
      .name("Kebab Restaurant")
      .build()

  SupplierDto APPLE_RESTAURANT = SupplierDto.builder()
      .id(APPLE_RESTAURANT_ID)
      .name("Apple Restaurant")
      .build()

  void withSampleSuppliers(SupplierFacade supplierFacade, SupplierDto ...suppliers) {
    suppliers.each { supplier ->
      supplierFacade.addSupplier(AddSupplierDto.builder().name(supplier.name).build())}
  }

  AddSupplierDto newSupplier(String name) {
    AddSupplierDto.builder()
        .name(name)
        .build()
  }

}