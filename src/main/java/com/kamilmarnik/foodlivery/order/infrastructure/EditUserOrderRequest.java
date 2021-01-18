package com.kamilmarnik.foodlivery.order.infrastructure;

import com.kamilmarnik.foodlivery.order.dto.EditUserOrderDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class EditUserOrderRequest {

  Collection<EditFoodRequest> editedFood;

  EditUserOrderDto toEditUserOrderDto(long orderId, long userOrderId) {
    final List<EditUserOrderDto.EditFoodDto> editedFood = this.editedFood.stream()
        .map(EditFoodRequest::toEditFoodDto)
        .collect(Collectors.toList());

    return EditUserOrderDto.builder()
        .orderId(orderId)
        .userOrderId(userOrderId)
        .editedFood(editedFood)
        .build();
  }

  @Getter
  @AllArgsConstructor
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class EditFoodRequest {

    long orderedFoodId;
    double price;
    int amount;

    private EditUserOrderDto.EditFoodDto toEditFoodDto() {
      return new EditUserOrderDto.EditFoodDto(this.orderedFoodId, this.price, this.amount);
    }

  }

}
