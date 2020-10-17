package com.kamilmarnik.foodlivery.order.exception;

public class OrderForSupplierAlreadyExists extends RuntimeException {

  public OrderForSupplierAlreadyExists(String message) {
    super(message);
  }

}