package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrderConfiguration {

  @Autowired
  ProposalRepository proposalRepository;

  @Autowired
  SupplierFacade supplierFacade;

  OrderFacade orderFacade(SupplierFacade supplierFacade) {
    return orderFacade(new InMemoryProposalRepository(), new InMemoryOrderRepository(), supplierFacade);
  }

  OrderFacade orderFacade(ProposalRepository proposalRepository, OrderRepository orderRepository, SupplierFacade supplierFacade) {
    return OrderFacade.builder()
        .proposalRepository(proposalRepository)
        .orderRepository(orderRepository)
        .supplierFacade(supplierFacade)
        .build();
  }

}
