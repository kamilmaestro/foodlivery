package com.kamilmarnik.foodlivery.utils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class DateUtils {

  public static Date nowPlusMinutes(int minutes) {
    return Date.from(
        LocalDateTime.now()
            .plusMinutes(minutes)
            .atZone(ZoneId.systemDefault())
            .toInstant()
    );
  }

}
