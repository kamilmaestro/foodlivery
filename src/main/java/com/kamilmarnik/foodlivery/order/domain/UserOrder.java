package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.UserOrderDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserOrder {

  @Setter(value = AccessLevel.PACKAGE)
  Long id;
  String orderUuid;
  OrderedFood orderedFood;
  Long orderedFor;
  LocalDateTime createdAt;

  UserOrder(String orderUuid, OrderedFood orderedFood, Long orderedFor) {
    this.orderUuid = orderUuid;
    this.orderedFood = orderedFood;
    this.orderedFor = orderedFor;
    this.createdAt = now();
  }

  UserOrderDto dto() {
    return UserOrderDto.builder()
        .id(this.id)
        .orderUuid(this.orderUuid)
        .foodId(this.orderedFood.getFoodId())
        .foodAmount(this.orderedFood.getAmount().getValue())
        .orderedFor(this.orderedFor)
        .createdAt(this.createdAt)
        .build();
  }

}
