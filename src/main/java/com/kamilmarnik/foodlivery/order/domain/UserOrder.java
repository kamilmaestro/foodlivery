package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.EditUserOrderDto;
import com.kamilmarnik.foodlivery.order.dto.OrderedFoodDto;
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto;
import com.kamilmarnik.foodlivery.order.dto.UserOrderWithFoodDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.Instant.now;
import static java.util.stream.Collectors.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_orders")
class UserOrder implements Serializable {

  @Setter(value = AccessLevel.PACKAGE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "uuid")
  String uuid;

  @Column(name = "order_uuid")
  String orderUuid;

  @Column(name = "ordered_for")
  Long orderedFor;

  @Column(name = "created_at")
  Instant createdAt;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_order_uuid", referencedColumnName = "uuid", updatable = false, insertable = false)
  Set<OrderedFood> food;

  UserOrder(String userOrderUuid, String orderUuid, Long orderedFor, Set<OrderedFood> orderedFood) {
    this.uuid = userOrderUuid;
    this.orderUuid = orderUuid;
    this.orderedFor = orderedFor;
    this.food = orderedFood;
    this.createdAt = now();
  }

  UserOrderDto dto() {
    final List<OrderedFoodDto> foodDto = this.food.stream()
        .map(OrderedFood::dto)
        .collect(toList());

    return UserOrderDto.builder()
        .id(this.id)
        .orderUuid(this.orderUuid)
        .orderedFor(this.orderedFor)
        .createdAt(this.createdAt)
        .orderedFood(foodDto)
        .build();
  }

  List<UserOrderWithFoodDto> withFoodDto() {
    return this.food.stream()
        .map(food ->
            UserOrderWithFoodDto.builder()
                .id(this.id)
                .orderUuid(this.orderUuid)
                .foodName(food.getName())
                .amountOfFood(food.getAmount().getValue())
                .foodPrice(food.getPrice().getValueAsDouble())
                .orderedFor(this.orderedFor)
                .build()
        ).collect(toList());
  }

  void edit(EditUserOrderDto editUserOrder) {
    final Map<Long, EditUserOrderDto.EditFoodDto> foodByIds = editUserOrder.getEditedFood().stream()
        .collect(toMap(EditUserOrderDto.EditFoodDto::getOrderedFoodId, Function.identity()));
    this.food
        .stream()
        .filter(food -> foodByIds.containsKey(food.getId()))
        .forEach(food -> {
          final EditUserOrderDto.EditFoodDto editedFood = foodByIds.get(food.getId());
          food.edit(editedFood.getAmount(), editedFood.getPrice());
        });
  }

}
