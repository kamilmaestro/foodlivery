package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.ProposalFoodDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposal_food")
class ProposalFood {

  @Id
  @Setter(value = AccessLevel.PACKAGE)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "proposal_uuid")
  String proposalUuid;

  @Column(name = "food_id")
  Long foodId;

  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "amount_of_food"))
  AmountOfFood amount;

  ProposalFood(String proposalUuid, Long foodId, Integer amount) {
    this.proposalUuid = proposalUuid;
    this.foodId = foodId;
    this.amount = new AmountOfFood(amount);
  }

  ProposalFoodDto dto() {
    return new ProposalFoodDto(this.foodId, this.amount.getValue());
  }

}
