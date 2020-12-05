package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.exception.CanNotBePurchaser;
import com.kamilmarnik.foodlivery.utils.TimeProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static com.kamilmarnik.foodlivery.utils.DateUtils.nowPlusMinutes;
import static java.time.LocalDateTime.now;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class OrderCreator {

  OrderExpirationConfig expirationConfig;
  TimeProvider timeProvider;

  AcceptedOrder makeOrderForSupplier(long supplierId, Set<Proposal> supplierProposals, long channelId) {
    checkIfUserCanBePurchaser(supplierProposals);
    return Order.acceptOrder(supplierId, channelId, supplierProposals);
  }

  Proposal createProposal(AddProposalDto addProposal) {
    return new Proposal(addProposal, createProposalExpiration());
  }

  private void checkIfUserCanBePurchaser(Set<Proposal> proposals) {
    proposals.stream()
        .filter(proposal -> proposal.getCreatedBy().equals(getLoggedUserId()))
        .findFirst()
        .orElseThrow(CanNotBePurchaser::new);
  }

  private ProposalExpiration createProposalExpiration() {
    final Instant expirationDate = nowPlusMinutes(expirationConfig.getExpirationAfterMinutes());
    return new ProposalExpiration(timeProvider.now(), expirationDate);
  }

}
