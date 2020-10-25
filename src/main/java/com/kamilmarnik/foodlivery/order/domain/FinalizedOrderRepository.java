package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface FinalizedOrderRepository extends JpaRepository<FinalizedOrder, Long> {

}
