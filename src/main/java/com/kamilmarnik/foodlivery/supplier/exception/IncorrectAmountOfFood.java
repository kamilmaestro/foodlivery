package com.kamilmarnik.foodlivery.supplier.exception;

public class IncorrectAmountOfFood extends RuntimeException {

  public IncorrectAmountOfFood(Integer amount) {
    super("Incorrect amount of food: " + amount);
  }

}