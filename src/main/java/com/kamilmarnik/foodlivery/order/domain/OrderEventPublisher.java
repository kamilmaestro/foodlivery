package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderDto;
import com.kamilmarnik.foodlivery.order.event.OrderFinished;
import com.kamilmarnik.foodlivery.utils.TimeProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class OrderEventPublisher {

  ApplicationEventPublisher eventPublisher;
  TimeProvider timeProvider;

  void notifyOrderFinish(OrderDto finishedOrder) {
    final List<OrderFinished.UserOrderFinished> userOrders = finishedOrder.getUserOrders().stream()
        .map(OrderFinished.UserOrderFinished::fromDto)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
    final OrderFinished orderFinished = OrderFinished.builder()
        .orderId(finishedOrder.getId())
        .supplierId(finishedOrder.getSupplierId())
        .channelId(finishedOrder.getChannelId())
        .purchaserId(finishedOrder.getPurchaserId())
        .finishedAt(timeProvider.now())
        .userOrders(userOrders)
        .build();

    eventPublisher.publishEvent(orderFinished);
  }

}
