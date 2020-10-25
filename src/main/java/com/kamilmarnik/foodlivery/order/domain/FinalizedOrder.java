package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Set;

import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.FINALIZED;
import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.ORDERED;
import static java.time.LocalDateTime.now;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
class FinalizedOrder {

  @Setter(value = AccessLevel.PACKAGE)
  @Getter(value = AccessLevel.PACKAGE)
  Long id;
  Long supplierId;
  Long channelId;
  Long purchaserId;
  LocalDateTime finalizedAt;

  @Enumerated(EnumType.STRING)
  OrderStatus status = FINALIZED;

  Set<UserOrder> userOrders;

  FinalizedOrder(Order order) {
    this.id = order.getId();
    this.supplierId = order.getSupplierId();
    this.channelId = order.getChannelId();
    this.purchaserId = order.getPurchaserId();
    this.finalizedAt = now();
    this.userOrders = order.getUserOrders();
  }

  public FinalizedOrderDto dto() {
    return FinalizedOrderDto.builder()
        .id(this.id)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .purchaserId(this.purchaserId)
        .finalizedAt(this.finalizedAt)
        .build();
  }

}
