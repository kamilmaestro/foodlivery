package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class FinalizedOrderDto {

  long id;
  long supplierId;
  long channelId;
  long purchaserId;
  LocalDateTime finalizedAt;
  List<UserOrderDto> userOrders;

}
