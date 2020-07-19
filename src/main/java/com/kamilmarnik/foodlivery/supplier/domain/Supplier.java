package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.supplier.dto.FoodDto;
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto;
import com.kamilmarnik.foodlivery.supplier.dto.SupplierMenuDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Getter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
class Supplier {

  Long id;
  String name;
  String phoneNumber;
  String email;
  LocalDateTime added;

  SupplierDto dto() {
    return SupplierDto.builder()
        .id(this.id)
        .name(this.name)
        .build();
  }

  SupplierMenuDto menuDto(List<FoodDto> supplierFood) {
    return SupplierMenuDto.builder()
        .supplier(dto())
        .menu(supplierFood)
        .build();
  }

}
