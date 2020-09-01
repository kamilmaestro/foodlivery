package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter;
import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class OrderCreator {

  LoggedUserGetter loggedUserGetter;

  Order createProposal(AddProposalDto addProposal) {
    return Order.builder()
        .supplierId(addProposal.getSupplierId())
        .userId(loggedUserGetter.getLoggedUserId())
        .foodId(addProposal.getFoodId())
        .createdAt(LocalDateTime.now())
        .build();
  }

}
