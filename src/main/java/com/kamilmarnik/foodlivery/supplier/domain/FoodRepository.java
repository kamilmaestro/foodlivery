package com.kamilmarnik.foodlivery.supplier.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface FoodRepository extends JpaRepository<Food, Long> {

  List<Food> findAllBySupplierId(long supplierId);

}
