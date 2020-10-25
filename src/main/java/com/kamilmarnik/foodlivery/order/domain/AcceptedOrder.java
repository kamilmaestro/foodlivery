package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.AcceptedOrderDto;

interface AcceptedOrder {

  AcceptedOrderDto acceptedDto();

  FinalizedOrder finalizeOrder();

}
