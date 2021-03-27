package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto;
import com.kamilmarnik.foodlivery.utils.TimeProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.kamilmarnik.foodlivery.utils.DateUtils.nowPlusMinutes;
import static java.time.LocalDateTime.now;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.*;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class OrderCreator {

  SupplierFacade supplierFacade;
  OrderExpirationConfig expirationConfig;
  TimeProvider timeProvider;

  AcceptedOrder makeOrder(long supplierId, Set<Proposal> supplierProposals, long channelId) {
    final String orderUuid = randomUUID().toString();
    final Collection<FoodDto> food = getFoodByIds(supplierProposals);
    final Set<UserOrder> userOrders = supplierProposals.stream()
        .map(proposal -> makeOrderForUser(orderUuid, proposal, food))
        .collect(toSet());

    return Order.acceptOrder(orderUuid, supplierId, channelId, userOrders);
  }

  Proposal createProposal(AddProposalDto addProposal) {
    final String proposalUuid = randomUUID().toString();
    final Set<ProposalFood> proposalFood = addProposal.getFood().stream()
        .map(food -> new ProposalFood(proposalUuid, food.getFoodId(), food.getAmountOfFood()))
        .collect(toSet());

    return new Proposal(
        proposalUuid, createProposalExpiration(), addProposal.getSupplierId(), addProposal.getChannelId(), proposalFood
    );
  }

  private ProposalExpiration createProposalExpiration() {
    final Instant expirationDate = nowPlusMinutes(expirationConfig.getExpirationAfterMinutes());
    return new ProposalExpiration(timeProvider.now(), expirationDate);
  }

  private Collection<FoodDto> getFoodByIds(Collection<Proposal> supplierProposals) {
    final List<Long> foodIds = supplierProposals.stream()
        .map(Proposal::getFoodIds)
        .flatMap(Collection::stream)
        .collect(toList());

    return supplierFacade.getFoodByIds(foodIds);
  }

  private UserOrder makeOrderForUser(String orderUuid, Proposal proposal, Collection<FoodDto> food) {
    final String userOrderUuid = randomUUID().toString();
    final Set<OrderedFood> orderedFood = getOrderedFood(userOrderUuid, proposal, food);

    return proposal.makeOrderForUser(orderUuid, userOrderUuid, orderedFood);
  }

  private Set<OrderedFood> getOrderedFood(String userOrderUuid, Proposal proposal, Collection<FoodDto> food) {
    final Map<Long, AmountOfFood> amountByFoodIds = proposal.getProposalFood().stream()
        .collect(toMap(ProposalFood::getFoodId, ProposalFood::getAmount));

    return food.stream()
        .filter(dto -> amountByFoodIds.containsKey(dto.getId()))
        .map(dto -> {
          final AmountOfFood amount = amountByFoodIds.getOrDefault(dto.getId(), AmountOfFood.ONE);
          return new OrderedFood(userOrderUuid, dto.getName(), amount, dto.getPrice());
        }).collect(toSet());
  }

}
