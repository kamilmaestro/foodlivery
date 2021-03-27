package com.kamilmarnik.foodlivery.supplier.exception;

public class FoodNotFound extends RuntimeException {

  public FoodNotFound(String message) {
    super(message);
  }

}