package com.kamilmarnik.foodlivery.image.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
class ImageConfiguration {

  @Autowired
  ImageRepository imageRepository;

  ImageFacade createImageFacade(ImageRepository imageRepository) {
    return ImageFacade.builder()
        .imageRepository(imageRepository)
        .build();
  }

  @Bean
  ImageFacade createImageFacade() {
    return createImageFacade(imageRepository);
  }

}
