package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.supplier.dto.FoodDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Getter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Food {

  Long id;
  String name;
  Long supplierId;
  Money price;

  FoodDto dto() {
    return FoodDto.builder()
        .id(this.id)
        .name(this.name)
        .supplierId(this.supplierId)
        .price(this.price.getValueAsDouble())
        .build();
  }

}
