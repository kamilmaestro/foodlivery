package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class UserOrderDto {

  long id;
  String orderUuid;
  long orderedFor;
  Instant createdAt;
  List<OrderedFoodDto> orderedFood;

}
