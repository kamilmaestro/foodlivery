package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<Order, Long> {

  Page<Order> findAllBySupplierId(long supplierId, PageRequest pageRequest);

}
