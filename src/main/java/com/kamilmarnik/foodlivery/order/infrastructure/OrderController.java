package com.kamilmarnik.foodlivery.order.infrastructure;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.order.domain.OrderFacade;
import com.kamilmarnik.foodlivery.order.dto.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
  ResponseEntity<ProposalDto> createProposal(@RequestBody AddProposalDto addProposal) {
    return ResponseEntity.ok(orderFacade.createProposal(addProposal));
  }

  @GetMapping("/channel/{channelId}")
  ResponseEntity<Page<SimplifiedOrderDto>> findOrdersInChannel(@PathVariable long channelId,
                                                               @ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(orderFacade.findOrdersInChannel(channelId, pageInfo));
  }

  @PostMapping("/purchaser")
  ResponseEntity<OrderDto> becomePurchaser(@RequestBody NewPurchaserDto newPurchaser) {
    return ResponseEntity.ok(orderFacade.becomePurchaser(newPurchaser));
  }

  @PostMapping("/{orderId}/finalize")
  ResponseEntity<OrderDto> finalizeOrder(@PathVariable long orderId) {
    return ResponseEntity.ok(orderFacade.finalizeOrder(orderId));
  }

  @DeleteMapping("/{orderId}/user-order/{userOrderId}")
  ResponseEntity<OrderDto> removeUserOrder(@PathVariable long orderId, @PathVariable long userOrderId) {
    return ResponseEntity.ok(orderFacade.removeUserOrder(userOrderId, orderId));
  }

  @PostMapping("/{orderId}/finish")
  ResponseEntity<OrderDto> finishOrder(@PathVariable long orderId) {
    return ResponseEntity.ok(orderFacade.finishOrder(orderId));
  }

  @GetMapping("/{orderId}")
  ResponseEntity<OrderDto> getOrder(@PathVariable long orderId) {
    return ResponseEntity.ok(orderFacade.getOrderDto(orderId));
  }

  @GetMapping("/channel/{channelId}/proposals")
  ResponseEntity<Page<ProposalDto>> findChannelProposals(@PathVariable long channelId,
                                                                @ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(orderFacade.findChannelProposals(channelId, pageInfo));
  }

  @GetMapping("/user")
  ResponseEntity<Page<OrderIdentityDto>> findUserOrders(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(orderFacade.findUserOrders(pageInfo));
  }

  @PutMapping("/{orderId}/user-order/{userOrderId}")
  ResponseEntity<Void> editUserOrder(@PathVariable long orderId,
                                            @PathVariable long userOrderId,
                                            @RequestBody EditUserOrderRequest editUserOrder) {
    orderFacade.editUserOrder(editUserOrder.toEditUserOrderDto(orderId, userOrderId));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{orderId}")
  ResponseEntity<Void> resignFromPurchase(@PathVariable long orderId) {
    orderFacade.resignFromPurchase(orderId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
