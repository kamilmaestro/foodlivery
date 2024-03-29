package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class OrderedFoodDto {

  long id;
  String userOrderUuid;
  String foodName;
  int amountOfFood;
  double foodPrice;

}
