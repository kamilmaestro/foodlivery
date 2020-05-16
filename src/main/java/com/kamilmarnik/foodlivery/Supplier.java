package com.kamilmarnik.foodlivery;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder(toBuilder = true)
@Getter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
class Supplier {
  Long id;
  String name;

  SupplierDto dto() {
    return SupplierDto.builder()
        .id(this.id)
        .name(this.name)
        .build();
  }
}
