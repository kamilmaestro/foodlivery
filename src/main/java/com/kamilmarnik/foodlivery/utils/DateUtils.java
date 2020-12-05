package com.kamilmarnik.foodlivery.utils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.time.temporal.ChronoUnit.MINUTES;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class DateUtils {

  public static Date currentDatePlusMinutes(int minutes) {
    return Date.from(nowPlusMinutes(minutes));
  }

  public static Instant nowPlusMinutes(int minutes) {
    return Instant.now().plus(minutes, MINUTES);
  }

}
