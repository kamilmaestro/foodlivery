package com.kamilmarnik.foodlivery.order.exception;

public class UserOrderRemovalForbidden extends RuntimeException {

  public UserOrderRemovalForbidden(Long userOrderId) {
    super("Can not remove user order with an Id: " + userOrderId);
  }

}