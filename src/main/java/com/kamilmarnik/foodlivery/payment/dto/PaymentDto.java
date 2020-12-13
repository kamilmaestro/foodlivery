package com.kamilmarnik.foodlivery.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class PaymentDto {

  long id;
  long purchaserId;
  long payerId;
  long supplierId;
  long channelId;
  double price;
  Instant createdAt;
  PaymentStatusDto status;
  List<PaymentDetailsDto> details;

  @Getter
  @AllArgsConstructor
  @EqualsAndHashCode
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class PaymentDetailsDto {

    String foodName;
    int foodAmount;
    double foodPrice;

  }

}
