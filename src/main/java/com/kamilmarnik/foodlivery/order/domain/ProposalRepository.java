package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

interface ProposalRepository extends JpaRepository<Proposal, Long> {

  Set<Proposal> findAllBySupplierId(long supplierId);

}
