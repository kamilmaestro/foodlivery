package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.exception.IllegalExpirationDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
final class ProposalExpiration {

  Instant createdAt;
  Instant expirationDate;

  ProposalExpiration(Instant createdAt, Instant expirationDate) {
    if (!expirationDate.isAfter(createdAt)) {
      throw new IllegalExpirationDate(
          "Expiration date: " + expirationDate.toString() + " must be after creation date: " + createdAt.toString()
      );
    }
    this.createdAt = createdAt;
    this.expirationDate = expirationDate;
  }

}