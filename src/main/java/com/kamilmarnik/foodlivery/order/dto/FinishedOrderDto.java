package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class FinishedOrderDto {

  long id;
  long supplierId;
  long channelId;
  long purchaserId;
  Instant createdAt;

}
