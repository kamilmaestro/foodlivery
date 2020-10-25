package com.kamilmarnik.foodlivery.order.exception;

public class OrderFinalizationForbidden extends RuntimeException {

  public OrderFinalizationForbidden(Long orderId) {
    super("Logged in user can not finalize order with id: " + orderId);
  }

}