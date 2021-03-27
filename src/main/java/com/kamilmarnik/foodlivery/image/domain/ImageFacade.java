package com.kamilmarnik.foodlivery.image.domain;

import com.kamilmarnik.foodlivery.image.dto.ImageDto;
import com.kamilmarnik.foodlivery.image.dto.ImageUploadedDto;
import com.kamilmarnik.foodlivery.image.exception.ImageNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageFacade {

  ImageRepository imageRepository;

  public ImageUploadedDto storeImage(MultipartFile file) {
    return imageRepository.save(Image.fromMultiPartFile(file)).toUploadImageResponse(file.getSize());
  }

  public ImageDto getImage(long imageId) {
    return imageRepository.findById(imageId).orElseThrow(ImageNotFound::new).dto();
  }

}
