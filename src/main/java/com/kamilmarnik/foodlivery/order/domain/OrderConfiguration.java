package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrderConfiguration {

  OrderFacade orderFacade(SupplierFacade supplierFacade) {
    return orderFacade(supplierFacade, new InMemoryProposalRepository(), new InMemoryOrderRepository());
  }

  OrderFacade orderFacade(SupplierFacade supplierFacade,
                          ProposalRepository proposalRepository,
                          OrderRepository orderRepository) {
    return OrderFacade.builder()
        .supplierFacade(supplierFacade)
        .proposalRepository(proposalRepository)
        .orderRepository(orderRepository)
        .orderCreator(new OrderCreator())
        .build();
  }

}
