package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.EditUserOrderDto;
import com.kamilmarnik.foodlivery.order.dto.OrderDto;

interface FinalizedOrder {

  OrderDto finalizedDto();

  FinalizedOrder removeUserOrderFromFinalizedOrder(long userOrderId);

  FinalizedOrder editUserOrder(EditUserOrderDto editUserOrder);

  FinishedOrder finishOrder();

}
