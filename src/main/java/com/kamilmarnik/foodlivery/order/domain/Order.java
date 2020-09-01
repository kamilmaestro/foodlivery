package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
class Order {

  Long id;
  Long supplierId;
  Long foodId;
  Long userId;
  LocalDateTime createdAt;

  public ProposalDto dto(FoodDto food) {
    return ProposalDto.builder()
        .proposalId(this.id)
        .supplierId(this.supplierId)
        .food(food)
        .userId(this.userId)
        .createdAt(createdAt)
        .build();
  }

}
