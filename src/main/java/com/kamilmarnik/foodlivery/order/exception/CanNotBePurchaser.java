package com.kamilmarnik.foodlivery.order.exception;

public class CanNotBePurchaser extends RuntimeException {

  public CanNotBePurchaser(String message) {
    super(message);
  }

}