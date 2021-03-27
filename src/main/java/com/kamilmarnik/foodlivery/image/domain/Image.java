package com.kamilmarnik.foodlivery.image.domain;

import com.kamilmarnik.foodlivery.image.dto.ImageDto;
import com.kamilmarnik.foodlivery.image.dto.ImageUploadedDto;
import com.kamilmarnik.foodlivery.image.exception.ImageStorageFault;
import com.kamilmarnik.foodlivery.image.exception.InvalidFileName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import java.io.IOException;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
@Entity
@Table(name = "images")
class Image {

  public static final int NAME_MAX_LENGTH = 255;
  public static final int NAME_MIN_LENGTH = 1;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "file_name")
  String fileName;

  @Column(name = "file_type")
  String fileType;

  @Lob
  @Column(name = "data")
  private byte[] data;

  static Image fromMultiPartFile(MultipartFile file)  {
    try {
      return Image.builder()
          .fileName(getCleanFileName(file))
          .fileType(file.getContentType())
          .data(file.getBytes())
          .build();
    } catch (IOException e) {
      throw new ImageStorageFault("Could not store image" + file.getOriginalFilename());
    }
  }

  ImageUploadedDto toUploadImageResponse(long imageSize) {
    return ImageUploadedDto.builder()
        .id(this.id)
        .fileName(this.fileName)
        .fileType(this.fileType)
        .fileDownloadUrl(getImageDownloadUrl())
        .size(imageSize)
        .build();
  }

  ImageDto dto() {
    return ImageDto.builder()
        .id(this.id)
        .fileName(this.fileName)
        .fileType(this.fileType)
        .data(this.data)
        .build();
  }

  private static String getCleanFileName(MultipartFile file) {
    final String fileName = StringUtils.cleanPath(
        Optional.ofNullable(file.getOriginalFilename()).orElseThrow(InvalidFileName::new)
    );

    return Optional.of(fileName)
        .filter(name -> !name.contains("..") && name.length() <= NAME_MAX_LENGTH && name.length() >= NAME_MIN_LENGTH)
        .orElseThrow(InvalidFileName::new);
  }

  private String getImageDownloadUrl() {
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/image/")
        .path(String.valueOf(id))
        .toUriString();
  }

}
