package com.kamilmarnik.foodlivery.supplier.infrastructure;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import com.kamilmarnik.foodlivery.supplier.dto.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/supplier")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SupplierController {

  SupplierFacade supplierFacade;

  @Autowired
  public SupplierController(@Autowired SupplierFacade supplierFacade) {
    this.supplierFacade = supplierFacade;
  }

  @PostMapping("/")
  public ResponseEntity<SupplierDto> addSupplier(@RequestBody AddSupplierDto addSupplier) {
    return ResponseEntity.ok(supplierFacade.addSupplier(addSupplier));
  }

  @GetMapping("/")
  public ResponseEntity<Page<SupplierDto>> findSuppliers(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(supplierFacade.findAllSuppliers(pageInfo));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SupplierMenuDto> getSupplier(@PathVariable("id") long id) {
    return ResponseEntity.ok(supplierFacade.getSupplierMenu(id));
  }

  @PostMapping("/food")
  public ResponseEntity<FoodDto> addFood(@RequestBody AddFoodToMenuDto addFoodToMenu) {
    return ResponseEntity.ok(supplierFacade.addFoodToSupplierMenu(addFoodToMenu));
  }

}
