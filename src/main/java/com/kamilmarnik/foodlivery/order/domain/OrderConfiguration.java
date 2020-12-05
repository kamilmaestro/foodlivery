package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import com.kamilmarnik.foodlivery.utils.SystemTimeProvider;
import com.kamilmarnik.foodlivery.utils.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrderConfiguration {

  OrderFacade orderFacade(SupplierFacade supplierFacade,
                          OrderExpirationConfig expirationConfig,
                          TimeProvider timeProvider) {
    return orderFacade(
        supplierFacade, expirationConfig, timeProvider, new InMemoryProposalRepository(), new InMemoryOrderRepository()
    );
  }

  @Bean
  OrderFacade orderFacade(SupplierFacade supplierFacade,
                          OrderExpirationConfig expirationConfig,
                          TimeProvider timeProvider,
                          ProposalRepository proposalRepository,
                          OrderRepository orderRepository) {
    return OrderFacade.builder()
        .supplierFacade(supplierFacade)
        .timeProvider(timeProvider)
        .proposalRepository(proposalRepository)
        .orderRepository(orderRepository)
        .orderCreator(new OrderCreator(expirationConfig, timeProvider))
        .build();
  }

}
