package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class UserOrderDto {

  long id;
  String orderUuid;
  long foodId;
  int foodAmount;
  long orderedFor;
  LocalDateTime createdAt;

}
