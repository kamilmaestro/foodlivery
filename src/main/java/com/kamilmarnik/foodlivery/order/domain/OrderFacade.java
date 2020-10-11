package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.dto.OrderDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderFacade {

  ProposalRepository proposalRepository;
  OrderRepository orderRepository;
  SupplierFacade supplierFacade;

  public ProposalDto createProposal(AddProposalDto addProposal) {
    supplierFacade.checkIfFoodExists(addProposal.getFoodId(), addProposal.getSupplierId());
    final Proposal proposal = new Proposal(addProposal);

    return proposalRepository.save(proposal).dto();
  }

  public void becomePurchaser(long supplierId) {
    supplierFacade.checkIfSupplierExists(supplierId);
    final Set<Proposal> supplierProposals = proposalRepository.findAllBySupplierId(supplierId);
    final Set<Order> orders = supplierProposals.stream()
        .map(Proposal::makeOrder)
        .collect(toSet());
    orderRepository.saveAll(orders);
  }

  public Page<OrderDto> findOrdersFromSupplier(long supplierId, PageInfo pageInfo) {
    return orderRepository.findAllBySupplierId(supplierId, pageInfo.toPageRequest())
        .map(Order::dto);
  }

}
