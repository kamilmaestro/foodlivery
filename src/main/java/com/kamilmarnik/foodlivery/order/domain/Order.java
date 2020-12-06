package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.*;
import com.kamilmarnik.foodlivery.order.exception.OrderFinalizationForbidden;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.*;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
class Order implements AcceptedOrder, FinalizedOrder, FinishedOrder, Serializable {

  @Setter(value = AccessLevel.PACKAGE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "uuid")
  String uuid;

  @Column(name = "supplier_id")
  Long supplierId;

  @Column(name = "channel_id")
  Long channelId;

  @Column(name = "purchaser_id")
  Long purchaserId;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  OrderStatus status;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "order_uuid", referencedColumnName = "uuid", updatable = false, insertable = false)
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

  private Order(Order order, OrderStatus status) {
    this.id = order.getId();
    this.uuid = order.getUuid();
    this.supplierId = order.getSupplierId();
    this.channelId = order.getChannelId();
    this.purchaserId = order.getPurchaserId();
    this.createdAt = order.getCreatedAt();
    this.status = status;
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
    return new Order(this, FINALIZED);
  }

  @Override
  public FinalizedOrder removeUserOrder(long userOrderId) {
    this.userOrders.removeIf(userOrder -> userOrder.getId().equals(userOrderId));
    return new Order(this, FINALIZED);
  }

  @Override
  public FinishedOrder finishOrder() {
    return new Order(this, FINISHED);
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

  @Override
  public FinishedOrderDto finishedDto() {
    return FinishedOrderDto.builder()
        .id(this.id)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .purchaserId(this.purchaserId)
        .createdAt(this.createdAt)
        .build();
  }

  public OrderWithStatusDto orderWithStatusDto() {
    final List<UserOrderDto> userOrders = this.userOrders.stream()
        .map(UserOrder::dto)
        .collect(toList());

    return OrderWithStatusDto.builder()
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
