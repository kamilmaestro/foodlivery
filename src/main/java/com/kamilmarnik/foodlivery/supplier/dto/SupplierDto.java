package com.kamilmarnik.foodlivery.supplier.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EqualsAndHashCode
public final class SupplierDto {

  long id;
  String name;
  String phoneNumber;
  String address;
  Long imageId;
  LocalDateTime createdAt;

}
