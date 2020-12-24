package com.kamilmarnik.foodlivery.order.exception;

public class OrderNotFound extends RuntimeException {

  public OrderNotFound(Long orderId, String status) {
    super("Can not find an order with id: " + orderId + " and status: " + status);
  }

  public OrderNotFound(Long orderId) {
    super("Can not find an order with id: " + orderId);
  }

}