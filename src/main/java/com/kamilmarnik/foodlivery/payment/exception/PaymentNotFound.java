package com.kamilmarnik.foodlivery.payment.exception;

public class PaymentNotFound extends RuntimeException {

  public PaymentNotFound(long paymentId) {
    super("Can not find a payment with an Id: " + paymentId);
  }

}