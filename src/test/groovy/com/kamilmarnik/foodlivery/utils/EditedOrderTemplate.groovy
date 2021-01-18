package com.kamilmarnik.foodlivery.utils

import com.kamilmarnik.foodlivery.order.dto.EditUserOrderDto
import com.kamilmarnik.foodlivery.order.dto.OrderDto
import com.kamilmarnik.foodlivery.order.dto.UserOrderDto
import com.kamilmarnik.foodlivery.user.dto.UserDto
import static java.util.stream.Collectors.toList

class EditedOrderTemplate {

  private final OrderDto order
  private UserDto user

  EditedOrderTemplate(OrderDto order) {
    this.order = order
  }

  EditedOrderTemplate forUser(UserDto user) {
    this.user = user
    return this
  }

  EditUserOrderDto editFood(int amount, double price) {
    return EditUserOrderDto.builder()
        .orderId(this.order.id)
        .userOrderId(getUserOrderForUser().id)
        .editedFood(createEditedFood(amount, price))
        .build()
  }

  private UserOrderDto getUserOrderForUser() {
    return this.order.userOrders.stream()
        .filter({ userOrder -> userOrder.orderedFor == this.user.userId })
        .findFirst()
        .orElse(null)
  }

  private Collection<EditUserOrderDto.EditFoodDto> createEditedFood(int amount, double price) {
    return getUserOrderForUser().orderedFood.stream()
        .map({ food -> new EditUserOrderDto.EditFoodDto(food.id, price, amount) })
        .collect(toList())
  }

}
