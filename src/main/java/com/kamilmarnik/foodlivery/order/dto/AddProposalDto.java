package com.kamilmarnik.foodlivery.order.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AddProposalDto {

  long supplierId;
  long channelId;
  Collection<AddFood> food;

  public Collection<Long> getFoodIds() {
    return this.food.stream()
        .map(AddFood::getFoodId)
        .collect(Collectors.toList());
  }

  @AllArgsConstructor
  @Getter
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class AddFood {

    long foodId;
    Integer amountOfFood;

  }

}
