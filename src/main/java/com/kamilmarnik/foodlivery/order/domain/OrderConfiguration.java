package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrderConfiguration {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  LoggedUserGetter loggedUserGetter;

  @Autowired
  SupplierFacade supplierFacade;

  OrderFacade orderFacade(LoggedUserGetter loggedUserGetter, SupplierFacade supplierFacade) {
    return orderFacade(new InMemoryOrderRepository(), loggedUserGetter, supplierFacade);
  }

  OrderFacade orderFacade(OrderRepository orderRepository, LoggedUserGetter loggedUserGetter, SupplierFacade supplierFacade) {
    return OrderFacade.builder()
        .orderRepository(orderRepository)
        .orderCreator(new OrderCreator(loggedUserGetter))
        .supplierFacade(supplierFacade)
        .build();
  }

}
