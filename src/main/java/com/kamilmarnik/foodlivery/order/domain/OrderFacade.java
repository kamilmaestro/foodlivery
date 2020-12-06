package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.order.dto.*;
import com.kamilmarnik.foodlivery.order.exception.OrderForSupplierAlreadyExists;
import com.kamilmarnik.foodlivery.order.exception.OrderNotFound;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import com.kamilmarnik.foodlivery.utils.TimeProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.stream.Collectors;

import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.*;
import static com.kamilmarnik.foodlivery.order.domain.ProposalStatus.WAITING;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderFacade {

  SupplierFacade supplierFacade;
  TimeProvider timeProvider;
  ProposalRepository proposalRepository;
  OrderRepository orderRepository;
  OrderCreator orderCreator;

  public ProposalDto createProposal(AddProposalDto addProposal) {
    supplierFacade.checkIfFoodExists(addProposal.getFoodId(), addProposal.getSupplierId());
    final Proposal proposal = orderCreator.createProposal(addProposal);

    return proposalRepository.save(proposal).dto();
  }

  public AcceptedOrderDto becomePurchaser(NewPurchaserDto newPurchaser) {
    supplierFacade.checkIfSupplierExists(newPurchaser.getSupplierId());
    checkIfOrderForSupplierAlreadyExists(newPurchaser.getSupplierId(), newPurchaser.getChannelId());
    final Set<Proposal> supplierProposals = proposalRepository
        .findAllBySupplierIdAndChannelId(newPurchaser.getSupplierId(), newPurchaser.getChannelId());
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
    return proposalRepository.findByChannelIdAndStatus(channelId, WAITING, pageInfo.toPageRequest())
        .map(Proposal::dto);
  }

  public Page<OrderWithStatusDto> findNotFinishedOrders(long channelId, PageInfo pageInfo) {
    return orderRepository.findAllByChannelIdAndStatusNot(channelId, FINISHED, pageInfo.toPageRequest())
        .map(Order::orderWithStatusDto);
  }

  public AcceptedOrderDto getOrderDto(long id) {
    return getOrder(id, ORDERED).acceptedDto();
  }

  void updateExpiredProposals() {
    final Set<Proposal> expiredProposals = proposalRepository.findToExpire(timeProvider.now()).stream()
        .map(Proposal::expire)
        .collect(Collectors.toSet());
    if (!expiredProposals.isEmpty()) {
      proposalRepository.saveAll(expiredProposals);
    }
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

}
