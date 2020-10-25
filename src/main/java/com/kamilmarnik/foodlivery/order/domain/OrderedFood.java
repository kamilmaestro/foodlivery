package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.supplier.domain.AmountOfFood;
import com.kamilmarnik.foodlivery.supplier.exception.IncorrectAmountOfFood;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class OrderedFood {

  long foodId;
  AmountOfFood amount;

  OrderedFood(Long foodId, Integer amount) {
    requireNonNull(foodId);
    this.foodId = foodId;
    this.amount = new AmountOfFood(amount);
  }

}
