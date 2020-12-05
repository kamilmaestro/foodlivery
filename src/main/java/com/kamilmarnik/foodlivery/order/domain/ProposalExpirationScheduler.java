package com.kamilmarnik.foodlivery.order.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class ProposalExpirationScheduler {

  OrderFacade orderFacade;

  @Autowired
  ProposalExpirationScheduler(OrderFacade orderFacade) {
    this.orderFacade = orderFacade;
  }

  @Scheduled(fixedRate = 60000)
  void expireProposals() {
    orderFacade.updateExpiredProposals();
  }

}
