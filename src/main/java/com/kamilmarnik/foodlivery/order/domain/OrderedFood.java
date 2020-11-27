package com.kamilmarnik.foodlivery.order.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
final class OrderedFood {

  long foodId;
  AmountOfFood amount;

  OrderedFood(Long foodId, Integer amount) {
    requireNonNull(foodId);
    this.foodId = foodId;
    this.amount = new AmountOfFood(amount);
  }

}
