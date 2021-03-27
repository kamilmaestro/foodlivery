package com.kamilmarnik.foodlivery.payment.infrastructure;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import com.kamilmarnik.foodlivery.order.dto.SimplifiedOrderDto;
import com.kamilmarnik.foodlivery.payment.domain.PaymentFacade;
import com.kamilmarnik.foodlivery.payment.dto.PaymentDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/payment")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class PaymentController {

  PaymentFacade paymentFacade;

  @Autowired
  PaymentController(PaymentFacade paymentFacade) {
    this.paymentFacade = paymentFacade;
  }

  @GetMapping("/charges/")
  public ResponseEntity<Page<PaymentDto>> findUserCharges(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(paymentFacade.findUserCharges(pageInfo));
  }

  @GetMapping("/due/")
  public ResponseEntity<Page<PaymentDto>> findUserMoneyDue(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(paymentFacade.findUserMoneyDue(pageInfo));
  }

  @GetMapping("/archival/")
  public ResponseEntity<Page<PaymentDto>> getArchivalPayments(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(paymentFacade.getArchivalPayments(pageInfo));
  }

  @PostMapping("/{paymentId}")
  public ResponseEntity<Void> marksAsPaidOff(@PathVariable long paymentId) {
    paymentFacade.marksAsPaidOff(paymentId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
