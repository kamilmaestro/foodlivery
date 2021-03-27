package com.kamilmarnik.foodlivery.order.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kamilmarnik.foodlivery.order.dto.EditUserOrderDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class EditUserOrderRequest {

  EditUserOrderRequest(@JsonProperty("editedFood") Collection<EditFoodRequest> editedFood) {
    this.editedFood = editedFood;
  }

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
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  static final class EditFoodRequest {

    EditFoodRequest(@JsonProperty("id") long orderedFoodId,
                    @JsonProperty("foodPrice") double price,
                    @JsonProperty("amountOfFood") int amount) {
      this.orderedFoodId = orderedFoodId;
      this.price = price;
      this.amount = amount;
    }

    long orderedFoodId;
    double price;
    int amount;

    private EditUserOrderDto.EditFoodDto toEditFoodDto() {
      return new EditUserOrderDto.EditFoodDto(this.orderedFoodId, this.price, this.amount);
    }

  }

}
