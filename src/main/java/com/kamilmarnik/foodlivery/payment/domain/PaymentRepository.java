package com.kamilmarnik.foodlivery.payment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentRepository extends JpaRepository<Payment, Long> {

  Page<Payment> findAllByPayerId(long userId, Pageable pageable);

  Page<Payment> findAllByPurchaserId(long userId, Pageable pageable);

}
