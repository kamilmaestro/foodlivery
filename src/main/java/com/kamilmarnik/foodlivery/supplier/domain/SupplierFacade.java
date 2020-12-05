package com.kamilmarnik.foodlivery.supplier.domain;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.supplier.dto.*;
import com.kamilmarnik.foodlivery.supplier.exception.FoodNotFound;
import com.kamilmarnik.foodlivery.supplier.exception.SupplierNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

  public FoodDto addFoodToSupplierMenu(AddFoodToMenuDto foodToAdd) {
    checkIfSupplierExists(foodToAdd.getSupplierId());
    Food food = foodCreator.from(foodToAdd);

    return foodRepository.save(food).dto();
  }

  public SupplierMenuDto getSupplierMenu(long supplierId) {
    final Supplier supplier = getSupplier(supplierId);
    final List<Food> supplierFood = foodRepository.findAllBySupplierId(supplierId);

    return supplier.menuDto(supplierFood);
  }

  public Page<SupplierDto> findAllSuppliers(PageInfo pageInfo) {
    return supplierRepository.findAll(pageInfo.toPageRequest())
        .map(Supplier::dto);
  }

  public void checkIfFoodExists(long foodId, long supplierId) {
    getSupplier(supplierId);
    foodRepository.findById(foodId)
        .filter(food -> food.getSupplierId().equals(supplierId))
        .orElseThrow(() -> new FoodNotFound("Can not find food with id: " + foodId));
  }

  public void checkIfSupplierExists(long supplierId) {
    getSupplier(supplierId);
  }

  public List<SupplierDto> getSuppliersByIds(Collection<Long> supplierIds) {
    return supplierRepository.findAllById(supplierIds).stream()
        .map(Supplier::dto)
        .collect(toList());
  }

  public List<FoodDto> getFoodByIds(Collection<Long> foodIds) {
    return foodRepository.findAllById(foodIds).stream()
        .map(Food::dto)
        .collect(toList());
  }

  public Page<SupplierDto> searchSuppliers(String searchText, PageInfo pageInfo) {
    if (StringUtils.isEmpty(searchText)) {
      return findAllSuppliers(pageInfo);
    }
    final String toSearch = URLDecoder.decode(searchText, StandardCharsets.UTF_8);

    return supplierRepository.findAllByNameOrAddress(toSearch, pageInfo.toPageRequest())
        .map(Supplier::dto);
  }

  public Page<FoodDto> getSupplierFood(long supplierId, PageInfo pageInfo) {
    return foodRepository.findAllBySupplierId(supplierId, pageInfo.toPageRequest())
        .map(Food::dto);
  }

  private Supplier getSupplier(long supplierId) {
    return supplierRepository.findById(supplierId)
        .orElseThrow(SupplierNotFound::new);
  }

}
