package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
class Order {

  Long id;
  Long createdBy;
  LocalDateTime createdAt;
  OrderedFood orderedFood;
  Long purchaserId;

  OrderDto dto() {
    return OrderDto.builder()
        .id(this.id)
        .createdBy(this.createdBy)
        .createdAt(this.createdAt)
        .foodId(this.orderedFood.getFoodId())
        .supplierId(this.orderedFood.getSupplierId())
        .purchaserId(this.purchaserId)
        .build();
  }

}
