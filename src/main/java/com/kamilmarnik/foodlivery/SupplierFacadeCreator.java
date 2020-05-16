package com.kamilmarnik.foodlivery;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
class SupplierFacadeCreator {

  SupplierFacade supplierFacade(SupplierRepository supplierRepository) {
    return SupplierFacade.builder()
        .supplierRepository(supplierRepository)
        .build();
  }

  SupplierFacade supplierFacade() {
    return SupplierFacade.builder().build();
  }
}
