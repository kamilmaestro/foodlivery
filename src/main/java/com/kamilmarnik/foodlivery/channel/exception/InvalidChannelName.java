package com.kamilmarnik.foodlivery.channel.exception;

public class InvalidChannelName extends RuntimeException {

  public InvalidChannelName(String message) {
    super(message);
  }

}