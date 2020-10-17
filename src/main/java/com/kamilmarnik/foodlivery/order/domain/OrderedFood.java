package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.supplier.domain.AmountOfFood;
import com.kamilmarnik.foodlivery.supplier.exception.IncorrectAmountOfFood;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class OrderedFood {

  long foodId;
  AmountOfFood amount;

  OrderedFood(long foodId, Integer amount) {
    this.foodId = foodId;
    this.amount = new AmountOfFood(amount);
  }

}
