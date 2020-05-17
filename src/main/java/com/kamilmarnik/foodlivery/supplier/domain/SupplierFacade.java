package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.supplier.dto.*;
import com.kamilmarnik.foodlivery.supplier.exception.SupplierNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierFacade {

  SupplierRepository supplierRepository;
  FoodRepository foodRepository;

  public SupplierDto addSupplier(AddSupplierDto addSupplier) {
    Supplier toSave = Supplier.builder().name(addSupplier.getName()).build();
    return supplierRepository.save(toSave).dto();
  }

  public SupplierDto getSupplier(long supplierId) {
    return supplierRepository.findById(supplierId)
        .orElseThrow(SupplierNotFound::new)
        .dto();
  }

  public FoodDto addFoodToSupplierMenu(AddFoodToMenuDto addedFood) {
    Food food = Food.builder()
        .name(addedFood.getName())
        .supplierID(addedFood.getSupplierId())
        .build();

    return foodRepository.save(food).dto();
  }

  public SupplierMenuDto getSupplierMenu(long supplierId) {
    SupplierDto supplier = getSupplier(supplierId);
    List<FoodDto> supplierFood = foodRepository.findAllBySupplierId(supplierId).stream()
        .map(Food::dto)
        .collect(Collectors.toList());

    return SupplierMenuDto.withSupplier(supplier).addFoodToMenu(supplierFood);
  }
}
