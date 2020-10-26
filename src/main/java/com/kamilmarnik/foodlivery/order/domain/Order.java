package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto;
import com.kamilmarnik.foodlivery.order.dto.AcceptedOrderDto;
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto;
import com.kamilmarnik.foodlivery.order.exception.OrderFinalizationForbidden;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.FINALIZED;
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
class Order implements AcceptedOrder, FinalizedOrder {

  @Setter(value = AccessLevel.PACKAGE)
  Long id;
  String uuid;
  Long supplierId;
  Long channelId;
  Long purchaserId;
  LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  OrderStatus status;

  Set<UserOrder> userOrders;

  private Order(long supplierId, long channelId, long purchaserId, OrderStatus status, Set<Proposal> proposals) {
    this.uuid = randomUUID().toString();
    this.supplierId = supplierId;
    this.channelId = channelId;
    this.purchaserId = purchaserId;
    this.createdAt = now();
    this.status = status;
    this.userOrders = proposals.stream()
        .map(proposal -> proposal.makeOrderForUser(this.uuid))
        .collect(toSet());
  }

  private Order(Order order) {
    this.id = order.getId();
    this.uuid = order.getUuid();
    this.supplierId = order.getSupplierId();
    this.channelId = order.getChannelId();
    this.purchaserId = order.getPurchaserId();
    this.createdAt = order.getCreatedAt();
    this.status = FINALIZED;
    this.userOrders = order.getUserOrders();
  }

  static AcceptedOrder acceptOrder(long supplierId, long channelId, Set<Proposal> proposals) {
    return new Order(supplierId, channelId, getLoggedUserId(), ORDERED, proposals);
  }

  @Override
  public FinalizedOrder finalizeOrder() {
    if (!getLoggedUserId().equals(this.purchaserId)) {
      throw new OrderFinalizationForbidden(this.id);
    }
    return new Order(this);
  }

  @Override
  public FinalizedOrder removeUserOrder(long userOrderId) {
    this.userOrders.removeIf(userOrder -> userOrder.getId().equals(userOrderId));
    return new Order(this);
  }

  @Override
  public AcceptedOrderDto acceptedDto() {
    final List<UserOrderDto> userOrders = this.userOrders.stream()
        .map(UserOrder::dto)
        .collect(toList());

    return AcceptedOrderDto.builder()
        .id(this.id)
        .uuid(this.uuid)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .purchaserId(this.purchaserId)
        .createdAt(this.createdAt)
        .userOrders(userOrders)
        .build();
  }

  @Override
  public FinalizedOrderDto finalizedDto() {
    final List<UserOrderDto> userOrders = this.userOrders.stream()
        .map(UserOrder::dto)
        .collect(toList());

    return FinalizedOrderDto.builder()
        .id(this.id)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .purchaserId(this.purchaserId)
        .createdAt(this.createdAt)
        .userOrders(userOrders)
        .build();
  }

}
