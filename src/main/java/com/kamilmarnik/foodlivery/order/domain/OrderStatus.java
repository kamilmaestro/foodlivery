package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderWithStatusDto;

enum OrderStatus {

  FINALIZED,
  FINISHED,
  ORDERED;

  OrderWithStatusDto.OrderStatusDto dto() {
    switch (this) {
      case ORDERED:
        return OrderWithStatusDto.OrderStatusDto.ORDERED;
      case FINALIZED:
        return OrderWithStatusDto.OrderStatusDto.FINALIZED;
      default:
        throw new IllegalStateException("Can not obtain order status dto");
    }
  }

}
