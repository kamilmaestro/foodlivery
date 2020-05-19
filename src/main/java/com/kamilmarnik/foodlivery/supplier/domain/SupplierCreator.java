package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.supplier.dto.AddSupplierDto;

final class SupplierCreator {

  Supplier from(AddSupplierDto addSupplier) {
    return Supplier.builder()
        .name(addSupplier.getName())
        .build();
  }
}
