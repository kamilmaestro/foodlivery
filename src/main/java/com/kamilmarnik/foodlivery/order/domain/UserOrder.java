package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.UserOrderDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_orders")
class UserOrder {

  @Setter(value = AccessLevel.PACKAGE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "order_uuid")
  String orderUuid;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "foodId", column = @Column(name = "food_id")),
      @AttributeOverride(name = "amount.amount", column = @Column(name = "amount_of_food"))
  })
  OrderedFood orderedFood;

  @Column(name = "ordered_for")
  Long orderedFor;

  UserOrder(String orderUuid, OrderedFood orderedFood, Long orderedFor) {
    this.orderUuid = orderUuid;
    this.orderedFood = orderedFood;
    this.orderedFor = orderedFor;
  }

  UserOrderDto dto() {
    return UserOrderDto.builder()
        .id(this.id)
        .orderUuid(this.orderUuid)
        .foodId(this.orderedFood.getFoodId())
        .foodAmount(this.orderedFood.getAmount().getValue())
        .orderedFor(this.orderedFor)
        .build();
  }

}
