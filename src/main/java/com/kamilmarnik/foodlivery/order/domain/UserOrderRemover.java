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

  OrderDto removeUserOrder(long userOrderId, long orderId) {
    final Order order = getOrder(orderId, ORDERED, FINALIZED);
    if (order.getStatus().equals(ORDERED)) {
      final AcceptedOrder editedOrder = order.removeUserOrderFromAcceptedOrder(userOrderId);
      return orderRepository.saveAccepted(editedOrder).acceptedDto();
    } else {
      final FinalizedOrder editedOrder = order.removeUserOrderFromFinalizedOrder(userOrderId);
      return orderRepository.saveFinalized(editedOrder).finalizedDto();
    }
  }

  private Order getOrder(Long orderId, OrderStatus... status) {
    return orderRepository.findByIdAndStatusIn(orderId, Arrays.asList(status))
        .orElseThrow(() -> new OrderNotFound(orderId));
  }

}
