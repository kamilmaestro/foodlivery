package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.OrderedFoodDto;
import com.kamilmarnik.foodlivery.supplier.domain.Money;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ordered_food")
class OrderedFood {

  @Id
  @Setter(value = AccessLevel.PACKAGE)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "user_order_uuid")
  String userOrderUuid;

  @Column(name = "name")
  String name;

  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "amount"))
  AmountOfFood amount;

  @Embedded
  @AttributeOverride(name = "price", column = @Column(name = "price"))
  Money price;

  OrderedFood(String userOrderUuid, String name, AmountOfFood amount, double price) {
    this.userOrderUuid = userOrderUuid;
    this.name = name;
    this.amount = amount;
    this.price = new Money(price);
  }

  OrderedFoodDto dto() {
    return OrderedFoodDto.builder()
        .userOrderUuid(this.userOrderUuid)
        .foodName(this.name)
        .amountOfFood(this.amount.getValue())
        .foodPrice(this.price.getValueAsDouble())
        .build();
  }

}
