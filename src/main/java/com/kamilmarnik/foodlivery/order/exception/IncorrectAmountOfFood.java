package com.kamilmarnik.foodlivery.order.exception;

public class IncorrectAmountOfFood extends RuntimeException {

  public IncorrectAmountOfFood(String message) {
    super(message);
  }

}