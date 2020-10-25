package com.kamilmarnik.foodlivery.order.exception;

public class OrderNotFound extends RuntimeException {

  public OrderNotFound(String message) {
    super(message);
  }

}