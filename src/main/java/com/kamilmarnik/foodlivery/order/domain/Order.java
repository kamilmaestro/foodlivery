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
  OrderedFood orderedFood;
  LocalDateTime createdAt;

  OrderDto dto() {
    return OrderDto.builder().id(this.id).build();
  }

}
