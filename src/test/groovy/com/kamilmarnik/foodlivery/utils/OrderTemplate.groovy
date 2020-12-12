package com.kamilmarnik.foodlivery.utils

import com.kamilmarnik.foodlivery.order.dto.FinishedOrderDto
import com.kamilmarnik.foodlivery.order.event.OrderFinished
import com.kamilmarnik.foodlivery.samples.SampleChannels
import com.kamilmarnik.foodlivery.samples.SampleFood
import com.kamilmarnik.foodlivery.samples.SampleOrders
import com.kamilmarnik.foodlivery.samples.SampleSuppliers
import com.kamilmarnik.foodlivery.samples.SampleUsers

import java.time.Instant
import java.util.stream.Collectors

class OrderTemplate implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SampleChannels {

  private final FinishedOrderDto finishedOrder

  OrderTemplate(FinishedOrderDto finishedOrder) {
    this.finishedOrder = finishedOrder
  }


  OrderFinished finished(Instant time) {
    final List<OrderFinished.UserOrderFinished> userOrders = finishedOrder.getUserOrders().stream()
        .map({ order ->  OrderFinished.UserOrderFinished.fromDto(order) })
        .collect(Collectors.toList())

    return OrderFinished.builder()
        .orderId(finishedOrder.getId())
        .supplierId(finishedOrder.getSupplierId())
        .channelId(finishedOrder.getChannelId())
        .purchaserId(finishedOrder.getPurchaserId())
        .finishedAt(time)
        .userOrders(userOrders)
        .build()
  }

}
