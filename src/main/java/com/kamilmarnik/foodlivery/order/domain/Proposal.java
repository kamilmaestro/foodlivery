package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalFoodDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static com.kamilmarnik.foodlivery.order.domain.ProposalStatus.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "proposals")
class Proposal implements Serializable {

  @Id
  @Setter(value = AccessLevel.PACKAGE)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "uuid")
  String uuid;

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

  @Column(name = "channel_id")
  Long channelId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  ProposalStatus status;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "proposal_uuid", referencedColumnName = "uuid", updatable = false, insertable = false)
  Set<ProposalFood> proposalFood;

  Proposal(String uuid, ProposalExpiration expiration, long supplierId, long channelId, Set<ProposalFood> proposalFood) {
    this.uuid = uuid;
    this.createdBy = getLoggedUserId();
    this.expiration = expiration;
    this.supplierId = supplierId;
    this.channelId = channelId;
    this.status = WAITING;
    this.proposalFood = proposalFood;
  }

  UserOrder makeOrderForUser(String orderUuid, String userOrderUuid, Set<OrderedFood> orderedFood) {
    this.status = ORDERED;
    return new UserOrder(userOrderUuid, orderUuid, this.createdBy, orderedFood);
  }

  ProposalDto dto() {
    final List<ProposalFoodDto> proposalFoodDto = this.proposalFood.stream()
        .map(ProposalFood::dto)
        .collect(Collectors.toList());

    return ProposalDto.builder()
        .id(this.id)
        .supplierId(this.supplierId)
        .channelId(this.channelId)
        .createdBy(this.createdBy)
        .createdAt(this.expiration.getCreatedAt())
        .expirationDate(this.expiration.getExpirationDate())
        .food(proposalFoodDto)
        .build();
  }

  Proposal expire() {
    this.status = EXPIRED;
    return this;
  }

  Collection<Long> getFoodIds() {
    return this.proposalFood.stream()
        .map(ProposalFood::getFoodId)
        .collect(Collectors.toList());
  }

}
