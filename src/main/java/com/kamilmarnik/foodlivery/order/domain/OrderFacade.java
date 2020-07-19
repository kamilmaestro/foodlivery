package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import com.kamilmarnik.foodlivery.order.exception.OrderNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderFacade {

  OrderRepository orderRepository;

  public ProposalDto createProposal(AddProposalDto addProposal) {
    return orderRepository.save(Proposal.builder().build()).dto();
  }

  ProposalDto getProposal(long proposalId) {
    return orderRepository.findById(proposalId)
        .orElseThrow(() -> new OrderNotFound("Can not find proposal with id: " + proposalId))
        .dto();
  }

}
