package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderDto;
import com.kamilmarnik.foodlivery.order.exception.OrderNotFound;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.ORDERED;
import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.FINALIZED;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class UserOrderRemover {

  OrderRepository orderRepository;

  OrderDto removeUserOrder(long userOrderId, Order order) {
    if (order.getStatus().equals(ORDERED)) {
      final AcceptedOrder editedOrder = order.removeUserOrderFromAcceptedOrder(userOrderId);
      return orderRepository.saveOrder(editedOrder).acceptedDto();
    } else {
      final FinalizedOrder editedOrder = order.removeUserOrderFromFinalizedOrder(userOrderId);
      return orderRepository.saveOrder(editedOrder).finalizedDto();
    }
  }

}
