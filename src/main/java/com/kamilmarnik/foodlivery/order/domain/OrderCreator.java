package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter;
import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.exception.IncorrectAmountOfFood;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class OrderCreator {

  LoggedUserGetter loggedUserGetter;

  Order createProposal(AddProposalDto addProposal) {
    verifyAmountOfFood(addProposal.getAmountOfFood());
    return Order.builder()
        .supplierId(addProposal.getSupplierId())
        .userId(loggedUserGetter.getLoggedUserId())
        .foodId(addProposal.getFoodId())
        .amountOfFood(addProposal.getAmountOfFood())
        .createdAt(LocalDateTime.now())
        .build();
  }

  private void verifyAmountOfFood(Integer amountOfFood) {
    Optional.ofNullable(amountOfFood)
        .filter(this::isNaturalNumber)
        .orElseThrow(() -> new IncorrectAmountOfFood("Can not add a proposal with amount: " + amountOfFood));
  }

  private boolean isNaturalNumber(int number) {
    return number > 0;
  }

}
