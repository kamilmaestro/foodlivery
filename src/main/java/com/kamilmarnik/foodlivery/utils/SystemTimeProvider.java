package com.kamilmarnik.foodlivery.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public final class SystemTimeProvider implements TimeProvider {

  @Override
  public Instant now() {
    return Instant.now();
  }

}
