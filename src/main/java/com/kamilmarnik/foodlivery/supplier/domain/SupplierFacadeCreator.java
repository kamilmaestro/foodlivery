package com.kamilmarnik.foodlivery.supplier.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
class SupplierFacadeCreator {

  SupplierFacade supplierFacade(SupplierRepository supplierRepository, FoodRepository foodRepository) {
    return SupplierFacade.builder()
        .supplierRepository(supplierRepository)
        .foodRepository(foodRepository)
        .build();
  }

  SupplierFacade supplierFacade() {
    return SupplierFacade.builder().build();
  }
}
