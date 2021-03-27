package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.supplier.dto.AddFoodToMenuDto;

final class FoodCreator {

  Food from(AddFoodToMenuDto addFood) {
    return Food.builder()
        .name(addFood.getName())
        .supplierId(addFood.getSupplierId())
        .price(new Money(addFood.getPrice()))
        .imageId(addFood.getImageId())
        .build();
  }

}
