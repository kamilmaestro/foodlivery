package com.kamilmarnik.foodlivery.supplier.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Getter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class FoodDto {

  long id;
  String name;
  long supplierId;
  int amount;

}
