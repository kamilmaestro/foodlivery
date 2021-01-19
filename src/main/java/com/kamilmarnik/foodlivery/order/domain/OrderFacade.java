package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.order.dto.*;
import com.kamilmarnik.foodlivery.order.exception.AnyProposalForSupplierFound;
import com.kamilmarnik.foodlivery.order.exception.OrderForSupplierAlreadyExists;
import com.kamilmarnik.foodlivery.order.exception.OrderNotFound;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import com.kamilmarnik.foodlivery.utils.TimeProvider;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static com.kamilmarnik.foodlivery.order.domain.OrderStatus.*;
import static com.kamilmarnik.foodlivery.order.domain.ProposalStatus.WAITING;

@Transactional
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderFacade {

  SupplierFacade supplierFacade;
  TimeProvider timeProvider;
  ProposalRepository proposalRepository;
  OrderRepository orderRepository;
  OrderCreator orderCreator;
  OrderEventPublisher eventPublisher;
  UserOrderRemover userOrderRemover;

  public ProposalDto createProposal(AddProposalDto addProposal) {
    supplierFacade.checkIfFoodExists(addProposal.getFoodIds(), addProposal.getSupplierId());
    final Proposal proposal = orderCreator.createProposal(addProposal);

    return proposalRepository.save(proposal).dto();
  }

  public OrderDto becomePurchaser(NewPurchaserDto newPurchaser) {
    supplierFacade.checkIfSupplierExists(newPurchaser.getSupplierId());
    checkIfOrderForSupplierAlreadyExists(newPurchaser.getSupplierId(), newPurchaser.getChannelId());
    final Set<Proposal> supplierProposals = proposalRepository
        .findAllBySupplierIdAndChannelId(newPurchaser.getSupplierId(), newPurchaser.getChannelId());
    if (supplierProposals.isEmpty()) {
      throw new AnyProposalForSupplierFound("Can not find any proposal for the supplier with id: " + newPurchaser.getSupplierId());
    }
    final AcceptedOrder order = orderCreator
        .makeOrder(newPurchaser.getSupplierId(), supplierProposals, newPurchaser.getChannelId());

    return orderRepository.saveOrder(order).acceptedDto();
  }

  public OrderDto finalizeOrder(long orderId) {
    final AcceptedOrder order = getOrder(orderId, ORDERED);
    final FinalizedOrder finalizedOrder = order.finalizeOrder();

    return orderRepository.saveOrder(finalizedOrder).finalizedDto();
  }

  public OrderDto removeUserOrder(long userOrderId, long orderId) {
    final Order order = getOrder(orderId, ORDERED, FINALIZED);
    return userOrderRemover.removeUserOrder(userOrderId, order);
  }

  public OrderDto finishOrder(long orderId) {
    final FinalizedOrder finalizedOrder = getOrder(orderId, FINALIZED);
    final OrderDto finishedOrder = orderRepository.saveOrder(finalizedOrder.finishOrder()).finishedDto();
    eventPublisher.notifyOrderFinish(finishedOrder);

    return finishedOrder;
  }

  public Page<ProposalDto> findChannelProposals(long channelId, PageInfo pageInfo) {
    return proposalRepository.findByChannelIdAndStatus(channelId, WAITING, pageInfo.toPageRequest())
        .map(Proposal::dto);
  }

  public Page<SimplifiedOrderDto> findOrdersInChannel(long channelId, PageInfo pageInfo) {
    return orderRepository.findAllByChannelIdAndStatusIn(channelId, List.of(ORDERED, FINALIZED), pageInfo.toPageRequest())
        .map(Order::simplifiedDto);
  }

  public OrderDto getOrderDto(long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFound(id))
        .dto();
  }

  public Page<OrderIdentityDto> findUserOrders(PageInfo pageInfo) {
    final PageRequest pageRequest = pageInfo.toPageRequest(Sort.by("createdAt").descending());
    return orderRepository.findAllUserOrders(getLoggedUserId(), pageRequest)
        .map(Order::identityDto);
  }

  public void editUserOrder(EditUserOrderDto editUserOrder) {
    final FinalizedOrder order = getOrder(editUserOrder.getOrderId(), FINALIZED);
    final FinalizedOrder editedOrder = order.editUserOrder(editUserOrder);
    orderRepository.saveOrder(editedOrder);
  }

  public void resignFromPurchase(long orderId) {
    final Order order = getOrder(orderId, ORDERED, FINALIZED);
    final CancelledOrder cancelledOrder = order.getStatus().equals(ORDERED) ?
        order.resignFromAcceptedOrder()
        : order.resignFromFinalizedOrder();
    orderRepository.saveOrder(cancelledOrder);
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
    orderRepository.findBySupplierIdAndChannelIdAndStatusNot(supplierId, channelId, FINISHED).ifPresent(order -> {
      throw new OrderForSupplierAlreadyExists(
          "Can not create another order in this channel for the supplier with id: " + order.getSupplierId()
      );
    });
  }

  private Order getOrder(Long orderId, OrderStatus status) {
    return orderRepository.findByIdAndStatus(orderId, status)
        .orElseThrow(() -> new OrderNotFound(orderId, status.name()));
  }

  private Order getOrder(Long orderId, OrderStatus... status) {
    return orderRepository.findByIdAndStatusIn(orderId, Arrays.asList(status))
        .orElseThrow(() -> new OrderNotFound(orderId));
  }

}
