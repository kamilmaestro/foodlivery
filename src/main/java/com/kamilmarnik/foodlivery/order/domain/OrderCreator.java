package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.exception.CanNotBePurchaser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static java.time.LocalDateTime.now;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class OrderCreator {

  Order makeOrderForSupplier(long supplierId, Set<Proposal> supplierProposals, long channelId) {
    checkIfUserCanBePurchaser(supplierProposals);
    return new Order(supplierId, channelId, getLoggedUserId(), supplierProposals);
  }

  private void checkIfUserCanBePurchaser(Set<Proposal> proposals) {
    proposals.stream()
        .filter(proposal -> proposal.getCreatedBy().equals(getLoggedUserId()))
        .findFirst()
        .orElseThrow(() -> new CanNotBePurchaser("Can not be a purchaser without creating a proposal for this supplier"));
  }

}
