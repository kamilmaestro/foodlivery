package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.order.dto.*;
import com.kamilmarnik.foodlivery.order.exception.OrderForSupplierAlreadyExists;
import com.kamilmarnik.foodlivery.order.exception.OrderNotFound;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.Set;

import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.*;

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

  public AcceptedOrderDto becomePurchaser(NewPurchaserDto newPurchaser) {
    supplierFacade.checkIfSupplierExists(newPurchaser.getSupplierId());
    checkIfOrderForSupplierAlreadyExists(newPurchaser.getSupplierId(), newPurchaser.getChannelId());
    final Set<Proposal> supplierProposals = proposalRepository.findAllBySupplierId(newPurchaser.getSupplierId());
    final AcceptedOrder order = orderCreator
        .makeOrderForSupplier(newPurchaser.getSupplierId(), supplierProposals, newPurchaser.getChannelId());

    return orderRepository.saveAccepted(order).acceptedDto();
  }

  public FinalizedOrderDto finalizeOrder(long orderId) {
    final AcceptedOrder order = getOrder(orderId, ORDERED);
    final FinalizedOrder finalizedOrder = order.finalizeOrder();

    return orderRepository.saveFinalized(finalizedOrder).finalizedDto();
  }

  public FinalizedOrderDto removeUserOrder(long userOrderId, long orderId) {
    final FinalizedOrder finalizedOrder = getOrder(orderId, FINALIZED);
    final FinalizedOrder withoutRemovedUserOrder = finalizedOrder.removeUserOrder(userOrderId);

    return orderRepository.saveFinalized(withoutRemovedUserOrder).finalizedDto();
  }

  public FinishedOrderDto finishOrder(long orderId) {
    final FinalizedOrder finalizedOrder = getOrder(orderId, FINALIZED);
    final FinishedOrder finishedOrder = finalizedOrder.finishOrder();

    return orderRepository.saveFinished(finishedOrder).finishedDto();
  }

  public Page<ProposalDto> findChannelProposals(long channelId, PageInfo pageInfo) {
    return proposalRepository.findByChannelId(channelId, pageInfo.toPageRequest())
        .map(Proposal::dto);
  }

  private void checkIfOrderForSupplierAlreadyExists(long supplierId, long channelId) {
    orderRepository.findBySupplierIdAndChannelIdAndStatus(supplierId, channelId, ORDERED).ifPresent(order -> {
      throw new OrderForSupplierAlreadyExists(
          "Can not create another order in this channel for the supplier with id: " + order.getSupplierId()
      );
    });
  }

  private Order getOrder(Long orderId, OrderStatus status) {
    return orderRepository.findByIdAndStatus(orderId, status)
        .orElseThrow(() -> new OrderNotFound(orderId, status.name()));
  }

  public AcceptedOrderDto getOrderDto(long id) {
    return getOrder(id, ORDERED).acceptedDto();
  }

}
