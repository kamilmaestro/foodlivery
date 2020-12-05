package com.kamilmarnik.foodlivery.order.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Set;

interface ProposalRepository extends JpaRepository<Proposal, Long> {

  Set<Proposal> findAllBySupplierId(long supplierId);

  Page<Proposal> findByChannelIdAndStatus(long channelId, ProposalStatus status, Pageable pageable);

  @Query("SELECT p FROM Proposal p WHERE p.expiration.expirationDate <= :now AND p.status = 'WAITING'")
  Set<Proposal> findToExpire(@Param("now") Instant now);

}
