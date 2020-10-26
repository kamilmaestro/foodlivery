package com.kamilmarnik.foodlivery.order.exception;

public class OrderNotFound extends RuntimeException {

  public OrderNotFound(Long orderId, String status) {
    super("Can not find a(n) " + status + " order with id: " + orderId);
  }

}