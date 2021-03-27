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

  @Query("SELECT o FROM Order o WHERE o.supplierId = :supplierId AND o.channelId = :channelId " +
      "AND (o.status = 'ORDERED' OR o.status = 'FINALIZED')")
  Optional<Order> findActiveOrderForSupplierInChannel(@Param("supplierId") long supplierId, @Param("channelId") long channelId);

  Optional<Order> findByIdAndStatus(long orderId, OrderStatus status);

  @Query("SELECT o FROM Order o WHERE o.id = :orderId AND o.status IN (:status)")
  Optional<Order> findByIdAndStatusIn(@Param("orderId") long orderId, @Param("status") Collection<OrderStatus> status);

  Page<Order> findAllByChannelIdAndStatusIn(long channelId, Collection<OrderStatus> status, Pageable pageable);

  @Query("SELECT o FROM Order o WHERE o.purchaserId = :userId OR " +
      ":userId IN (SELECT uo.orderedFor FROM UserOrder uo WHERE uo.orderUuid = o.uuid)")
  Page<Order> findAllUserOrders(@Param("userId") long userId, Pageable pageable);

  Optional<Order> findByIdAndStatusNot(long orderId, OrderStatus status);

  @SuppressWarnings("unchecked")
  default <T> T saveOrder(T order) {
    requireNonNull(order);
    final Order savedOrder = this.save((Order) order);

    return (T) savedOrder;
  }

}
