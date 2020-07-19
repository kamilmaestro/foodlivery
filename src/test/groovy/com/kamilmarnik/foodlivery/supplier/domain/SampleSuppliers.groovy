package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.AddSupplierDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto

trait SampleSuppliers {

  private static final long PIZZA_RESTAURANT_ID = 111L
  private static final long KEBAB_RESTAURANT_ID = 112L
  private static final long APPLE_RESTAURANT_ID = 113L

  private static final Map NEW_SUPPLIER_DEFAULT_VALUES = [
      "name" : "Supplier",
      "phoneNumber" : "123456789",
      "email" : "supplier@email.com"
  ]

  SupplierDto PIZZA_RESTAURANT = createSupplierDto(PIZZA_RESTAURANT_ID, "Pizza Restaurant")
  SupplierDto KEBAB_RESTAURANT = createSupplierDto(KEBAB_RESTAURANT_ID, "Kebab Restaurant")
  SupplierDto APPLE_RESTAURANT = createSupplierDto(APPLE_RESTAURANT_ID, "Apple Restaurant")

  static void withSampleSuppliers(SupplierFacade supplierFacade, SupplierDto ...suppliers) {
    suppliers.each { supplier ->
      supplierFacade.addSupplier(newSupplier(name: supplier.name))}
  }

  static AddSupplierDto newSupplier(Map<String, Object> properties = [:]) {
    properties = NEW_SUPPLIER_DEFAULT_VALUES + properties

    return AddSupplierDto.builder()
        .name(properties.name as String)
        .phoneNumber(properties.phoneNumber as String)
        .email(properties.email as String)
        .build()
  }

  private static SupplierDto createSupplierDto(long id, String name) {
    return SupplierDto.builder()
        .id(id)
        .name(name)
        .build()
  }

}