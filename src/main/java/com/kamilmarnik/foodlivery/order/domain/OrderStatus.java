package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderStatusDto;

enum OrderStatus {

  CANCELLED,
  FINALIZED,
  FINISHED,
  ORDERED;

  OrderStatusDto dto() {
    switch (this) {
      case CANCELLED:
        return OrderStatusDto.CANCELLED;
      case FINALIZED:
        return OrderStatusDto.FINALIZED;
      case FINISHED:
        return OrderStatusDto.FINISHED;
      case ORDERED:
        return OrderStatusDto.ORDERED;
      default:
        throw new IllegalStateException("Can not obtain order status dto");
    }
  }

}
