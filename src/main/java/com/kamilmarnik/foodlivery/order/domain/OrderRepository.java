package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> findBySupplierIdAndChannelIdAndStatus(long supplierId, long channelId, OrderStatus status);

  Optional<Order> findByIdAndStatus(long orderId, OrderStatus status);

  @SuppressWarnings("unchecked")
  default <T extends AcceptedOrder> T saveAccepted(T order) {
    requireNonNull(order);
    final Order savedOrder = this.save((Order) order);

    return (T) savedOrder;
  }

  @SuppressWarnings("unchecked")
  default <T extends FinalizedOrder> T saveFinalized(T order) {
    requireNonNull(order);
    final Order savedOrder = this.save((Order) order);

    return (T) savedOrder;
  }

  @SuppressWarnings("unchecked")
  default <T extends FinishedOrder> T saveFinished(T order) {
    requireNonNull(order);
    final Order savedOrder = this.save((Order) order);

    return (T) savedOrder;
  }

}
