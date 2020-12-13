package com.kamilmarnik.foodlivery.payment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentRepository extends JpaRepository<Payment, Long> {

  Page<Payment> findAllByPayerIdAndStatusNot(long userId, PaymentStatus status, Pageable pageable);

  Page<Payment> findAllByPurchaserIdAndStatusNot(long userId, PaymentStatus status, Pageable pageable);

}
