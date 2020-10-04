package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.supplier.exception.IncorrectAmountOfFood;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AmountOfFood {

  int amount;

  public AmountOfFood(Integer amount) {
    this.amount = getVerifiedAmountOfFood(amount);
  }

  public int getValue() {
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
