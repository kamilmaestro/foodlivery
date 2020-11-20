package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.supplier.exception.InvalidFoodPrice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Optional;

import static java.math.RoundingMode.HALF_UP;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
final class Money {

  private static final int DECIMAL_PLACES = 2;

  BigDecimal price;

  Money(Double price) {
    this.price = Optional.ofNullable(price)
      .filter(this::isMoney)
      .map(BigDecimal::valueOf)
      .orElseThrow(() -> new InvalidFoodPrice("Can not add a food with price: " + price));
  }

  double getValueAsDouble() {
    return this.price.setScale(DECIMAL_PLACES, HALF_UP).doubleValue();
  }

  private boolean isMoney(double value) {
    return value > 0;
  }

}
