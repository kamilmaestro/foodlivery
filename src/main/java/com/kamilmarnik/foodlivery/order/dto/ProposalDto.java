package com.kamilmarnik.foodlivery.order.dto;

import com.kamilmarnik.foodlivery.supplier.dto.FoodDto;
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
  long supplierId;
  long userId;
  LocalDateTime createdAt;
  FoodDto food;

}
