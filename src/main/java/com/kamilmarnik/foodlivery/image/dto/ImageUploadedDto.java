package com.kamilmarnik.foodlivery.image.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ImageUploadedDto {

  long id;
  String fileName;
  String fileType;
  String fileDownloadUrl;
  long size;

}
