package com.kamilmarnik.foodlivery.order.exception;

public class CanNotBePurchaser extends RuntimeException {

  public CanNotBePurchaser() {
    super("Can not be a purchaser without creating a proposal for this supplier");
  }

}