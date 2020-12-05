package com.kamilmarnik.foodlivery.supplier.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface FoodRepository extends JpaRepository<Food, Long> {

  List<Food> findAllBySupplierId(long supplierId);

  Page<Food> findAllBySupplierId(long supplierId, Pageable pageable);

}
