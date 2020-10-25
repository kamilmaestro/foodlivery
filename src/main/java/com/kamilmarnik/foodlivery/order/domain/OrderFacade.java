package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto;
import com.kamilmarnik.foodlivery.order.dto.AcceptedOrderDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import com.kamilmarnik.foodlivery.order.exception.OrderForSupplierAlreadyExists;
import com.kamilmarnik.foodlivery.order.exception.OrderNotFound;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.ORDERED;

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

  public AcceptedOrderDto becomePurchaser(long supplierId, long channelId) {
    supplierFacade.checkIfSupplierExists(supplierId);
    checkIfOrderForSupplierAlreadyExists(supplierId, channelId);
    final Set<Proposal> supplierProposals = proposalRepository.findAllBySupplierId(supplierId);
    final AcceptedOrder order = orderCreator.makeOrderForSupplier(supplierId, supplierProposals, channelId);

    return orderRepository.saveAccepted(order).acceptedDto();
  }

  public FinalizedOrderDto finalizeOrder(long orderId) {
    final AcceptedOrder order = getAcceptedOrder(orderId);
    final FinalizedOrder finalizedOrder = order.finalizeOrder();

    return orderRepository.saveFinalized(finalizedOrder).finalizedDto();
  }

  private void checkIfOrderForSupplierAlreadyExists(long supplierId, long channelId) {
    orderRepository.findBySupplierIdAndChannelIdAndStatus(supplierId, channelId, ORDERED).ifPresent(order -> {
      throw new OrderForSupplierAlreadyExists(
          "Can not create another order in this channel for the supplier with id: " + order.getSupplierId()
      );
    });
  }

  private AcceptedOrder getAcceptedOrder(Long orderId) {
    return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFound(orderId));
  }

}
