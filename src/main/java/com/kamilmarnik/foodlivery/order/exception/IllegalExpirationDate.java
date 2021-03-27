package com.kamilmarnik.foodlivery.order.exception;

public class IllegalExpirationDate extends RuntimeException {

  public IllegalExpirationDate(String message) {
    super(message);
  }

}