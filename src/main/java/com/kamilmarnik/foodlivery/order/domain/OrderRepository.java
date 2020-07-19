package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<Proposal, Long> {
}
