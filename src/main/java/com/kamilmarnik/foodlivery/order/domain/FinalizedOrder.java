package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderDto;

interface FinalizedOrder {

  OrderDto finalizedDto();

  FinalizedOrder removeUserOrder(long userOrderId);

  FinishedOrder finishOrder();

}
