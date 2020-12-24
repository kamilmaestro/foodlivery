package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class OrderIdentityDto {

  long id;
  String uuid;
  long supplierId;
  long channelId;
  long purchaserId;
  Instant createdAt;
  OrderStatusDto status;

}
