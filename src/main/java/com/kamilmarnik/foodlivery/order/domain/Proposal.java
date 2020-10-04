package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static java.time.LocalDateTime.now;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
class Proposal {

  Long id;
  Long createdBy;
  OrderedFood orderedFood;
  LocalDateTime createdAt;

  Proposal(AddProposalDto addProposal) {
    this.createdAt = now();
    this.createdBy = getLoggedUserId();
    this.orderedFood = new OrderedFood(addProposal.getFoodId(), addProposal.getSupplierId(), addProposal.getAmountOfFood());
  }

  ProposalDto dto() {
    return ProposalDto.builder()
        .proposalId(this.id)
        .foodId(this.orderedFood.getFoodId())
        .supplierId(this.orderedFood.getSupplierId())
        .userId(this.createdBy)
        .createdAt(this.createdAt)
        .build();
  }

}
