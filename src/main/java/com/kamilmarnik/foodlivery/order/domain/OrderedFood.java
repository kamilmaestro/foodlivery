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
  long supplierId;
  AmountOfFood amount;

  OrderedFood(long foodId, long supplierId, Integer amount) {
    this.foodId = foodId;
    this.amount = new AmountOfFood(amount);
    this.supplierId = supplierId;
  }

}
