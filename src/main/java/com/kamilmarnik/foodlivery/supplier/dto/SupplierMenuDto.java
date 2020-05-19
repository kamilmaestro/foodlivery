package com.kamilmarnik.foodlivery.supplier.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SupplierMenuDto {
  
  SupplierDto supplier;
  List<FoodDto> menu;

  private SupplierMenuDto(SupplierDto supplier) {
    this.supplier = supplier;
    this.menu = new ArrayList<>();
  }

  public static SupplierMenuDto withSupplier(SupplierDto supplier) {
    return new SupplierMenuDto(supplier);
  }

  public SupplierMenuDto addFoodToMenu(List<FoodDto> toAdd) {
    this.menu.addAll(toAdd);
    return this;
  }

}
