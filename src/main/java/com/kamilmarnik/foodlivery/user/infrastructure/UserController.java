package com.kamilmarnik.foodlivery.user.infrastructure;

import com.kamilmarnik.foodlivery.user.domain.UserFacade;
import com.kamilmarnik.foodlivery.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserController {

  UserFacade userFacade;

  @Autowired
  UserController(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @PostMapping("/ids")
  public ResponseEntity<List<UserDto>> getUsersByIds(@RequestBody List<Long> userIds) {
    return ResponseEntity.ok(userFacade.getUsersByIds(userIds));
  }

}
