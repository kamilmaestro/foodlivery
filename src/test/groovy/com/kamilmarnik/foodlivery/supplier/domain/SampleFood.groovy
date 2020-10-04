package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.AddFoodToMenuDto

trait SampleFood {

  static AddFoodToMenuDto newFood(long supplierId, Integer amount) {
    return AddFoodToMenuDto.builder()
        .name("Food")
        .supplierId(supplierId)
        .amount(amount)
        .build()
  }

  static AddFoodToMenuDto newFood(long supplierId) {
    return newFood(supplierId, 1)
  }

}
