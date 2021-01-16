package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> findBySupplierIdAndChannelIdAndStatusNot(long supplierId, long channelId, OrderStatus status);

  Optional<Order> findByIdAndStatus(long orderId, OrderStatus status);

  @Query("SELECT o FROM Order o WHERE o.id = :orderId AND o.status IN (:status)")
  Optional<Order> findByIdAndStatusIn(@Param("orderId") long orderId, @Param("status") Collection<OrderStatus> status);

  Page<Order> findAllByChannelIdAndStatusNot(long channelId, OrderStatus status, Pageable pageable);

  @Query("SELECT o FROM Order o WHERE o.purchaserId = :userId OR " +
      ":userId IN (SELECT uo.orderedFor FROM UserOrder uo WHERE uo.orderUuid = o.uuid)")
  Page<Order> findAllUserOrders(@Param("userId") long userId, Pageable pageable);

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
