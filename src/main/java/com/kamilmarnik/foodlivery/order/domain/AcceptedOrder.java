package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderDto;

interface AcceptedOrder {

  OrderDto acceptedDto();

  AcceptedOrder removeUserOrderFromAcceptedOrder(long userOrderId);

  CancelledOrder resignFromAcceptedOrder();

  FinalizedOrder finalizeOrder();

}
