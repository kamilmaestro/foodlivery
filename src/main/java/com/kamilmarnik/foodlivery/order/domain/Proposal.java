package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static java.time.LocalDateTime.now;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposals")
class Proposal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "created_by")
  Long createdBy;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  @Column(name = "supplier_id")
  Long supplierId;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "foodId", column = @Column(name = "food_id")),
      @AttributeOverride(name = "amount.amount", column = @Column(name = "amount_of_food"))
  })
  OrderedFood orderedFood;

  @Column(name = "channel_id")
  Long channelId;

  Proposal(AddProposalDto addProposal) {
    this.createdBy = getLoggedUserId();
    this.createdAt = now();
    this.supplierId = addProposal.getSupplierId();
    this.orderedFood = new OrderedFood(addProposal.getFoodId(), addProposal.getAmountOfFood());
    this.channelId = addProposal.getChannelId();
  }

  UserOrder makeOrderForUser(String orderUuid) {
    return new UserOrder(orderUuid, this.orderedFood, this.createdBy);
  }

  ProposalDto dto() {
    return ProposalDto.builder()
        .proposalId(this.id)
        .foodId(this.orderedFood.getFoodId())
        .foodAmount(this.orderedFood.getAmount().getValue())
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .createdBy(this.createdBy)
        .createdAt(this.createdAt)
        .build();
  }

}
