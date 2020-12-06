package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class OrderWithStatusDto {

  long id;
  String uuid;
  long supplierId;
  long channelId;
  long purchaserId;
  LocalDateTime createdAt;
  List<UserOrderDto> userOrders;

}