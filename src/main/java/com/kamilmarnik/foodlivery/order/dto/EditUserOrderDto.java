package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class EditUserOrderDto {

  long orderId;
  long userOrderId;
  Collection<EditFoodDto> editedFood;

  @Getter
  @AllArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class EditFoodDto {

    long orderedFoodId;
    double price;
    int amount;

  }

}
