package com.kamilmarnik.foodlivery.payment.domain;

import com.kamilmarnik.foodlivery.payment.dto.PaymentStatusDto;

enum PaymentStatus {

  PAID_BY_PAYER,
  PAID_OFF,
  TO_PAY;

  PaymentStatusDto dto() {
    switch (this) {
      case PAID_BY_PAYER:
        return PaymentStatusDto.PAID_BY_PAYER;
      case PAID_OFF:
        return PaymentStatusDto.PAID_OFF;
      case TO_PAY:
        return PaymentStatusDto.TO_PAY;
      default:
        throw new IllegalStateException("Can not obtain payment status");
    }
  }

}
