package com.kamilmarnik.foodlivery.order.infrastructure;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.order.domain.OrderFacade;
import com.kamilmarnik.foodlivery.order.dto.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class OrderController {

  OrderFacade orderFacade;

  @Autowired
  OrderController(OrderFacade orderFacade) {
    this.orderFacade = orderFacade;
  }

  @PostMapping("/")
  public ResponseEntity<ProposalDto> createProposal(@RequestBody AddProposalDto addProposal) {
    return ResponseEntity.ok(orderFacade.createProposal(addProposal));
  }

  @GetMapping("/channel/{channelId}")
  public ResponseEntity<Page<SimplifiedOrderDto>> findNotFinishedOrders(@PathVariable long channelId,
                                                                        @ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(orderFacade.findNotFinishedOrders(channelId, pageInfo));
  }

  @PostMapping("/purchaser")
  public ResponseEntity<OrderDto> becomePurchaser(@RequestBody NewPurchaserDto newPurchaser) {
    return ResponseEntity.ok(orderFacade.becomePurchaser(newPurchaser));
  }

  @PostMapping("/{orderId}/finalize")
  public ResponseEntity<OrderDto> finalizeOrder(@PathVariable long orderId) {
    return ResponseEntity.ok(orderFacade.finalizeOrder(orderId));
  }

  @DeleteMapping("/{orderId}/userOrder/{userOrderId}")
  public ResponseEntity<OrderDto> removeOrder(@PathVariable long orderId, @PathVariable long userOrderId) {
    return ResponseEntity.ok(orderFacade.removeUserOrder(userOrderId, orderId));
  }

  @PostMapping("/{orderId}/finish")
  public ResponseEntity<OrderDto> finishOrder(@PathVariable long orderId) {
    return ResponseEntity.ok(orderFacade.finishOrder(orderId));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDto> getOrder(@PathVariable long orderId) {
    return ResponseEntity.ok(orderFacade.getOrderDto(orderId));
  }

  @GetMapping("/channel/{channelId}/proposals")
  public ResponseEntity<Page<ProposalDto>> findChannelProposals(@PathVariable long channelId,
                                                                @ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(orderFacade.findChannelProposals(channelId, pageInfo));
  }

  @GetMapping("/user")
  public ResponseEntity<Page<OrderIdentityDto>> findUserOrders(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(orderFacade.findUserOrders(pageInfo));
  }

}
