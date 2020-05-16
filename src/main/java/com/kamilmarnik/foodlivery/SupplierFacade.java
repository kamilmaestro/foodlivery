package com.kamilmarnik.foodlivery;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SupplierFacade {

  SupplierRepository supplierRepository;

  SupplierDto addSupplier(AddSupplierDto addSupplier) {
    Supplier toSave = Supplier.builder().name(addSupplier.getName()).build();
    return supplierRepository.save(toSave).dto();
  }

  SupplierDto getSupplier(long supplierId) {
    return supplierRepository.findById(supplierId)
        .orElseThrow(SupplierNotFound::new)
        .dto();
  }

}
