package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static com.kamilmarnik.foodlivery.order.domain.ProposalStatus.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposals")
class Proposal {

  @Id
  @Setter(value = AccessLevel.PACKAGE)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "created_by")
  Long createdBy;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "createdAt", column = @Column(name = "created_at")),
      @AttributeOverride(name = "expirationDate", column = @Column(name = "expiration_date"))
  })
  ProposalExpiration expiration;

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

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  ProposalStatus status;

  Proposal(AddProposalDto addProposal, ProposalExpiration expiration) {
    this.createdBy = getLoggedUserId();
    this.expiration = expiration;
    this.supplierId = addProposal.getSupplierId();
    this.orderedFood = new OrderedFood(addProposal.getFoodId(), addProposal.getAmountOfFood());
    this.channelId = addProposal.getChannelId();
    this.status = WAITING;
  }

  UserOrder makeOrderForUser(String orderUuid) {
    this.status = ORDERED;
    return new UserOrder(orderUuid, this.orderedFood, this.createdBy);
  }

  ProposalDto dto() {
    return ProposalDto.builder()
        .id(this.id)
        .foodId(this.orderedFood.getFoodId())
        .foodAmount(this.orderedFood.getAmount().getValue())
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .createdBy(this.createdBy)
        .createdAt(this.expiration.getCreatedAt())
        .expirationDate(this.expiration.getExpirationDate())
        .build();
  }

  Proposal expire() {
    this.status = EXPIRED;
    return this;
  }

}
