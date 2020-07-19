package com.kamilmarnik.foodlivery.supplier.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class AddSupplierDto {

  String name;
  String phoneNumber;
  String email;

}
