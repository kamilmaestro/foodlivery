package com.kamilmarnik.foodlivery.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class FixedTimeProvider implements TimeProvider {

  Clock clock;

  public FixedTimeProvider() {
    this.clock = Clock.systemUTC();
  }

  public void withFixedTime(Instant time) {
    this.clock = Clock.fixed(time, ZoneId.systemDefault());
  }

  @Override
  public Instant now() {
    return Instant.now(this.clock);
  }

}
