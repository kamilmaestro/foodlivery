package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.AddFoodToMenuDto

trait SampleFood {

  static final long FAKE_FOOD_ID = 0l
  AddFoodToMenuDto PIZZA = AddFoodToMenuDto.builder().name("Pizza").price(20).build()

  private static final Map NEW_FOOD_DEFAULT_VALUES = [
      "name" : "Supplier",
      "supplierId" : "123456789",
      "price" : "10.00"
  ]

  static AddFoodToMenuDto newFood(Map<String, Object> properties = [:]) {
    properties = NEW_FOOD_DEFAULT_VALUES + properties

    return AddFoodToMenuDto.builder()
        .name(properties.name as String)
        .supplierId(properties.supplierId as Long)
        .price(properties.price as Double)
        .build()
  }

}
