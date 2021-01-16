package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.*;
import com.kamilmarnik.foodlivery.order.exception.OrderFinalizationForbidden;
import com.kamilmarnik.foodlivery.order.exception.UserOrderRemovalForbidden;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.isAdmin;
import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.*;
import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

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
  Instant createdAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  OrderStatus status;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "order_uuid", referencedColumnName = "uuid", updatable = false, insertable = false)
  Set<UserOrder> userOrders;

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

  static AcceptedOrder acceptOrder(String orderUuid, long supplierId, long channelId, Set<UserOrder> userOrders) {
    return Order.builder()
        .uuid(orderUuid)
        .supplierId(supplierId)
        .channelId(channelId)
        .purchaserId(getLoggedUserId())
        .createdAt(now())
        .status(ORDERED)
        .userOrders(userOrders)
        .build();
  }

  @Override
  public FinalizedOrder finalizeOrder() {
    if (!getLoggedUserId().equals(this.purchaserId)) {
      throw new OrderFinalizationForbidden(this.id);
    }
    return new Order(this, FINALIZED);
  }

  @Override
  public AcceptedOrder removeUserOrderFromAcceptedOrder(long userOrderId) {
    checkIfCanRemoveUserOrderFromAcceptedOrder(userOrderId);
    this.userOrders.removeIf(userOrder -> userOrder.getId().equals(userOrderId));
    return new Order(this, ORDERED);
  }

  @Override
  public FinalizedOrder removeUserOrderFromFinalizedOrder(long userOrderId) {
    checkIfCanRemoveUserOrderFromFinalizedOrder(userOrderId);
    this.userOrders.removeIf(userOrder -> userOrder.getId().equals(userOrderId));
    return new Order(this, FINALIZED);
  }

  @Override
  public FinishedOrder finishOrder() {
    return new Order(this, FINISHED);
  }

  @Override
  public OrderDto acceptedDto() {
    return dto();
  }

  @Override
  public OrderDto finalizedDto() {
    return dto();
  }

  @Override
  public OrderDto finishedDto() {
    return dto();
  }

  SimplifiedOrderDto simplifiedDto() {
    return SimplifiedOrderDto.builder()
        .id(this.id)
        .uuid(this.uuid)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .purchaserId(this.purchaserId)
        .createdAt(this.createdAt)
        .status(this.status.dto())
        .userOrders(getUserOrdersWithFoodDto())
        .build();
  }

  OrderIdentityDto identityDto() {
    return OrderIdentityDto.builder()
        .id(this.id)
        .uuid(this.uuid)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .purchaserId(this.purchaserId)
        .createdAt(this.createdAt)
        .status(this.status.dto())
        .build();
  }

  OrderDto dto() {
    final List<UserOrderDto> userOrdersDto = this.userOrders.stream()
        .map(UserOrder::dto)
        .collect(toList());

    return OrderDto.builder()
        .id(this.id)
        .uuid(this.uuid)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .purchaserId(this.purchaserId)
        .createdAt(this.createdAt)
        .status(this.status.dto())
        .userOrders(userOrdersDto)
        .build();
  }

  private List<UserOrderWithFoodDto> getUserOrdersWithFoodDto() {
    return this.userOrders.stream()
        .map(UserOrder::withFoodDto)
        .flatMap(Collection::stream)
        .collect(toList());
  }

  private void checkIfCanRemoveUserOrderFromFinalizedOrder(long userOrderId) {
    if (!getLoggedUserId().equals(this.purchaserId) && !isAdmin()) {
      throw new UserOrderRemovalForbidden(userOrderId);
    }
  }

  private void checkIfCanRemoveUserOrderFromAcceptedOrder(long userOrderId) {
    if (!getLoggedUserId().equals(this.purchaserId) && !isAdmin() && !isBuyer(userOrderId)) {
      throw new UserOrderRemovalForbidden(userOrderId);
    }
  }

  private boolean isBuyer(long userOrderId) {
    final Optional<UserOrder> foundUserOrder = userOrders.stream()
        .filter(userOrder -> userOrder.getId() == userOrderId)
        .findFirst();

    return foundUserOrder
        .map(userOrder -> userOrder.getOrderedFor().equals(getLoggedUserId()))
        .orElse(false);
  }

}
