package com.kamilmarnik.foodlivery.user;

public class UserNotFound extends RuntimeException {

  public UserNotFound(String message) {
    super(message);
  }

}
