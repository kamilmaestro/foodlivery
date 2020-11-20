package com.kamilmarnik.foodlivery.infrastructure.httpError;

import com.kamilmarnik.foodlivery.order.exception.*;
import com.kamilmarnik.foodlivery.supplier.exception.*;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ErrorsHandler {

  @ExceptionHandler({
      FoodNotFound.class,
      SupplierNotFound.class,
      OrderNotFound.class,
      ProposalNotFound.class
  })
  public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
    return error(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler({
      CanNotBePurchaser.class,
      OrderFinalizationForbidden.class,
      OrderForSupplierAlreadyExists.class,
      IncorrectAmountOfFood.class,
      InvalidFoodPrice.class,
      InvalidSupplierData.class
  })
  public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
    return error(HttpStatus.BAD_REQUEST, e);
  }

  private ResponseEntity<ErrorResponse> error(HttpStatus status, Exception e) {
    return ResponseEntity.status(status).body(StringUtils.isBlank(e.getMessage()) ?
        new ErrorResponse(status, e.getClass().getSimpleName()) :
        new ErrorResponse(status, e.getClass().getSimpleName(), e.getMessage()));
  }

}
