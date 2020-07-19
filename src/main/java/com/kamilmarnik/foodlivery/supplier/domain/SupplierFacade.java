package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.supplier.dto.*;
import com.kamilmarnik.foodlivery.supplier.exception.SupplierNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierFacade {

  SupplierRepository supplierRepository;
  FoodRepository foodRepository;
  SupplierCreator supplierCreator;
  FoodCreator foodCreator;

  public SupplierDto addSupplier(AddSupplierDto addSupplier) {
    Supplier toSave = supplierCreator.from(addSupplier);
    return supplierRepository.save(toSave).dto();
  }

  public SupplierDto getSupplierDto(long supplierId) {
    return getSupplier(supplierId).dto();
  }

  public FoodDto addFoodToSupplierMenu(AddFoodToMenuDto addedFood) {
    checkIfSupplierExists(addedFood.getSupplierId());
    Food toSave = foodCreator.from(addedFood);

    return foodRepository.save(toSave).dto();
  }

  public SupplierMenuDto getSupplierMenu(long supplierId) {
    Supplier supplier = getSupplier(supplierId);
    List<FoodDto> supplierFood = foodRepository.findAllBySupplierId(supplierId).stream()
        .map(Food::dto)
        .collect(Collectors.toList());

    return supplier.menuDto(supplierFood);
  }

  public Page<SupplierDto> findAllSuppliers(PageInfo pageInfo) {
    return supplierRepository.findAll(pageInfo.toPageRequest())
        .map(Supplier::dto);
  }

  private void checkIfSupplierExists(long supplierId) {
    getSupplier(supplierId);
  }

  private Supplier getSupplier(long supplierId) {
    return supplierRepository.findById(supplierId)
            .orElseThrow(SupplierNotFound::new);
  }

}
