package com.kamilmarnik.foodlivery.supplier.domain

import com.kamilmarnik.foodlivery.supplier.dto.AddFoodToMenuDto

trait SampleFood {

  static AddFoodToMenuDto newFood(long supplierId) {
    AddFoodToMenuDto.builder()
        .name("Food")
        .supplierId(supplierId)
        .build()
  }

}