package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto;

interface FinalizedOrder {

  FinalizedOrderDto finalizedDto();

  FinalizedOrder removeUserOrder(long userOrderId);

  FinishedOrder finishOrder();

}