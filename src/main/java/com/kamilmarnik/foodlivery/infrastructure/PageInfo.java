package com.kamilmarnik.foodlivery.infrastructure;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class PageInfo {

  private static final int DEFAULT_PAGE_NUMBER = 0;
  private static final int DEFAULT_PAGE_SIZE = 10;

  Integer pageNumber;
  Integer pageSize;

  public PageRequest toPageRequest() {
    return PageRequest.of(
        Optional.ofNullable(pageNumber)
            .filter(number -> number >= 0)
            .orElse(DEFAULT_PAGE_NUMBER),
        Optional.ofNullable(pageSize)
            .filter(size -> size > 0)
            .orElse(DEFAULT_PAGE_SIZE)
    );
  }

}
