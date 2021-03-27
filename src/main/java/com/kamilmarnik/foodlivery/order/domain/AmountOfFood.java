package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.supplier.exception.IncorrectAmountOfFood;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class AmountOfFood {

  static final AmountOfFood ONE = new AmountOfFood(1);

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
        .orElseThrow(() -> new IncorrectAmountOfFood(amountOfFood));
  }

  private boolean isNaturalNumber(int number) {
    return number > 0;
  }

}
