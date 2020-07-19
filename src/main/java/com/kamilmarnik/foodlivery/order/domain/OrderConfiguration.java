package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrderConfiguration {

  @Autowired
  OrderRepository orderRepository;

  OrderFacade orderFacade() {
    return OrderFacade.builder()
        .orderRepository(new InMemoryOrderRepository())
        .build();
  }

  OrderFacade orderFacade(OrderRepository orderRepository) {
    return OrderFacade.builder()
        .orderRepository(orderRepository)
        .build();
  }

}
