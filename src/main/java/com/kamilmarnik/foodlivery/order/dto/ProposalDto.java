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
public final class ProposalDto {

  long id;
  long foodId;
  int foodAmount;
  long supplierId;
  long channelId;
  long createdBy;
  Instant createdAt;
  Instant expirationDate;

}
