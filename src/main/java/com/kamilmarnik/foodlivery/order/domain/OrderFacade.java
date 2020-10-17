package com.kamilmarnik.foodlivery.order.domain;

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

  public OrderDto becomePurchaser(long supplierId) {
    supplierFacade.checkIfSupplierExists(supplierId);
    checkIfOrderForSupplierAlreadyExists(supplierId);
    final Set<Proposal> supplierProposals = proposalRepository.findAllBySupplierId(supplierId);
    final Order order = orderCreator.makeOrderForSupplier(supplierId, supplierProposals);
    orderRepository.save(order);

    return order.dto();
  }

  private void checkIfOrderForSupplierAlreadyExists(long supplierId) {
    orderRepository.findBySupplierId(supplierId).ifPresent(order -> {
      throw new OrderForSupplierAlreadyExists("Can not create another for the supplier with id: " + order.getSupplierId());
    });
  }

}
