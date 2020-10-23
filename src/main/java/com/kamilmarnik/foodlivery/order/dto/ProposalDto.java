package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ProposalDto {

  long proposalId;
  long foodId;
  int foodAmount;
  long supplierId;
  long channelId;
  long createdBy;
  LocalDateTime createdAt;

}
