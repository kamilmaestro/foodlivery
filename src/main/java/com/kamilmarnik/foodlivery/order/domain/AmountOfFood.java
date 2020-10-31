package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.supplier.exception.IncorrectAmountOfFood;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import java.util.Optional;

@Embeddable
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class AmountOfFood {

  int amount;

  AmountOfFood(Integer amount) {
    this.amount = getVerifiedAmountOfFood(amount);
  }

  int getValue() {
    return this.amount;
  }

  private int getVerifiedAmountOfFood(Integer amountOfFood) {
    return Optional.ofNullable(amountOfFood)
        .filter(this::isNaturalNumber)
        .orElseThrow(() -> new IncorrectAmountOfFood("Incorrect amount of food: " + amountOfFood));
  }

  private boolean isNaturalNumber(int number) {
    return number > 0;
  }

}
