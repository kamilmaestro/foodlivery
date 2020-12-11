package com.kamilmarnik.foodlivery.order.event;

import com.kamilmarnik.foodlivery.order.dto.UserOrderDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Collection;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class OrderFinished {

  long orderId;
  long supplierId;
  long channelId;
  long purchaserId;
  Instant finishedAt;
  Collection<UserOrderFinished> userOrders;

  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class UserOrderFinished {

    long userId;
    String foodName;
    int foodAmount;
    double foodPrice;

    public static UserOrderFinished fromDto(UserOrderDto dto) {
      return UserOrderFinished.builder()
          .userId(dto.getOrderedFor())
          .foodName(dto.getFoodName())
          .foodAmount(dto.getFoodAmount())
          .foodPrice(dto.getFoodPrice())
          .build();
    }

  }

}