package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderDto;
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.ORDERED;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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
  Long channelId;
  Long purchaserId;
  LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  OrderStatus status = ORDERED;

  Set<UserOrder> userOrders;

  Order(long supplierId, long channelId, long purchaserId, Set<Proposal> proposals) {
    this.uuid = randomUUID().toString();
    this.supplierId = supplierId;
    this.channelId = channelId;
    this.purchaserId = purchaserId;
    this.createdAt = now();
    this.userOrders = proposals.stream()
        .map(proposal -> proposal.makeOrderForUser(this.uuid))
        .collect(toSet());
  }

  FinalizedOrder finalizeOrder() {
    return new FinalizedOrder(this);
  }

  OrderDto dto() {
    final List<UserOrderDto> userOrders = this.userOrders.stream()
        .map(UserOrder::dto)
        .collect(toList());

    return OrderDto.builder()
        .id(this.id)
        .uuid(this.uuid)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .purchaserId(this.purchaserId)
        .createdAt(this.createdAt)
        .userOrders(userOrders)
        .build();
  }

}
