package com.kamilmarnik.foodlivery.image.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ImageDto {

  Long id;
  String fileName;
  String fileType;
  byte[] data;

}
