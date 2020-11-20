package com.kamilmarnik.foodlivery.infrastructure.httpError;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
class ErrorResponse {

  private static final String NO_MESSAGE_AVAILABLE = "No message available!";

  HttpStatus status;
  String errorCode;
  String message;
  String errorClassName;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  LocalDateTime date;

  ErrorResponse(HttpStatus status, String errorClassName) {
    this.status = status;
    this.errorClassName = errorClassName;
    this.message = NO_MESSAGE_AVAILABLE;
    this.errorCode = String.valueOf(status.value());
    this.date = LocalDateTime.now();
  }

  ErrorResponse(HttpStatus status, String errorClassName, String message) {
    this.status = status;
    this.errorClassName = errorClassName;
    this.message = message;
    this.errorCode = String.valueOf(status.value());
    this.date = LocalDateTime.now();
  }

}
