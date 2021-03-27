package com.kamilmarnik.foodlivery.order.exception;

public class UserOrderEditionForbidden extends RuntimeException {

  public UserOrderEditionForbidden(long userOrderId) {
    super("Can not edit user order with an ID: " + userOrderId);
  }

}