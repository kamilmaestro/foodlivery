package com.kamilmarnik.foodlivery.payment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.DoubleStream;

interface PaymentRepository extends JpaRepository<Payment, Long> {

  Page<Payment> findAllByPayerIdAndStatusNot(long userId, PaymentStatus status, Pageable pageable);

  Page<Payment> findAllByPurchaserIdAndStatusNot(long userId, PaymentStatus status, Pageable pageable);

  @Query("SELECT p FROM Payment p " +
      "WHERE p.status = 'PAID_OFF' AND (p.purchaserId = :userId OR p.payerId = :userId)")
  Page<Payment> findAllPaidOffByUserId(long userId, Pageable pageable);

}
