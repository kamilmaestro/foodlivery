package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.channel.domain.ChannelFacade;
import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.dto.OrderDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import com.kamilmarnik.foodlivery.order.exception.OrderForSupplierAlreadyExists;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderFacade {

  SupplierFacade supplierFacade;
  ProposalRepository proposalRepository;
  OrderRepository orderRepository;
  OrderCreator orderCreator;

  public ProposalDto createProposal(AddProposalDto addProposal) {
    supplierFacade.checkIfFoodExists(addProposal.getFoodId(), addProposal.getSupplierId());
    final Proposal proposal = new Proposal(addProposal);

    return proposalRepository.save(proposal).dto();
  }

  public OrderDto becomePurchaser(long supplierId, long channelId) {
    supplierFacade.checkIfSupplierExists(supplierId);
    checkIfOrderForSupplierAlreadyExists(supplierId, channelId);
    final Set<Proposal> supplierProposals = proposalRepository.findAllBySupplierId(supplierId);
    final Order order = orderCreator.makeOrderForSupplier(supplierId, supplierProposals, channelId);
    orderRepository.save(order);

    return order.dto();
  }

  private void checkIfOrderForSupplierAlreadyExists(long supplierId, long channelId) {
    orderRepository.findBySupplierIdAndChannelId(supplierId, channelId).ifPresent(order -> {
      throw new OrderForSupplierAlreadyExists(
          "Can not create another order in this channel for the supplier with id: " + order.getSupplierId()
      );
    });
  }

}
