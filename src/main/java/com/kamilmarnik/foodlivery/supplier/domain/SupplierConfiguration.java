package com.kamilmarnik.foodlivery.supplier.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
class SupplierConfiguration {

  SupplierFacade supplierFacade() {
    return SupplierFacade.builder()
        .supplierRepository(new InMemorySupplierRepository())
        .foodRepository(new InMemoryFoodRepository())
        .supplierCreator(new SupplierCreator())
        .foodCreator(new FoodCreator())
        .build();
  }

  SupplierFacade supplierFacade(SupplierRepository supplierRepository, FoodRepository foodRepository) {
    return SupplierFacade.builder()
        .supplierRepository(supplierRepository)
        .foodRepository(foodRepository)
        .supplierCreator(new SupplierCreator())
        .foodCreator(new FoodCreator())
        .build();
  }

}
