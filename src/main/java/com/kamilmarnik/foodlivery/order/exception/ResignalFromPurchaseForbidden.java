package com.kamilmarnik.foodlivery.order.exception;

public class ResignalFromPurchaseForbidden extends RuntimeException {

  public ResignalFromPurchaseForbidden(Long orderId) {
    super("Can not resign from being a purchaser for an order with an ID: " + orderId);
  }

}