package com.kamilmarnik.foodlivery.order.exception;

public class OrderNotFound extends RuntimeException {

  public OrderNotFound(Long orderId) {
    super("Can not find an order with id: " + orderId);
  }

}