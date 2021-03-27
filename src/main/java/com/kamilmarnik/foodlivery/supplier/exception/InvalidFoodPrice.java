package com.kamilmarnik.foodlivery.supplier.exception;

public class InvalidFoodPrice extends RuntimeException {

  public InvalidFoodPrice(String message) {
    super(message);
  }

}