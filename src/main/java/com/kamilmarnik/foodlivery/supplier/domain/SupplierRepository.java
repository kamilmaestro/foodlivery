package com.kamilmarnik.foodlivery.supplier.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface SupplierRepository extends JpaRepository<Supplier, Long> {

  @Query("SELECT s FROM Supplier s WHERE lower(s.name) LIKE lower(concat('%', :toSearch, '%')) OR lower(s.address) LIKE lower(concat('%', :toSearch, '%'))")
  Page<Supplier> findAllByNameOrAddress(@Param("toSearch") String toSearch, Pageable pageable);

}
