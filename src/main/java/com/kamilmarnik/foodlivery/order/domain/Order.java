package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderDto;
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
class Order {

  @Setter(value = AccessLevel.PACKAGE)
  Long id;
  String uuid;
  Long supplierId;
  Long purchaserId;
  LocalDateTime createdAt;
  Set<UserOrder> userOrders;

  Order(Long supplierId, Long purchaserId, Set<Proposal> proposals) {
    this.uuid = randomUUID().toString();
    this.supplierId = supplierId;
    this.purchaserId = purchaserId;
    this.createdAt = now();
    this.userOrders = proposals.stream()
        .map(proposal -> proposal.makeOrder(this.uuid))
        .collect(Collectors.toSet());
  }

  OrderDto dto() {
    final List<UserOrderDto> userOrders = this.userOrders.stream()
        .map(UserOrder::dto)
        .collect(Collectors.toList());

    return OrderDto.builder()
        .id(this.id)
        .uuid(this.uuid)
        .supplierId(this.supplierId)
        .purchaserId(this.purchaserId)
        .createdAt(this.createdAt)
        .userOrders(userOrders)
        .build();
  }

}
