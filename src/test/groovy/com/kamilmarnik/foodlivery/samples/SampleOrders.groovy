package com.kamilmarnik.foodlivery.samples

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto
import com.kamilmarnik.foodlivery.order.dto.NewPurchaserDto

trait SampleOrders {

  private static final Map NEW_Order_DEFAULT_VALUES = [
      "supplierId" : "12345",
      "foodId" : "12345",
      "amountOfFood" : "1",
      "channelId" : "12345"
  ]

  static AddProposalDto newProposal(Map<String, Object> properties = [:]) {
    properties = NEW_Order_DEFAULT_VALUES + properties

    return AddProposalDto.builder()
        .supplierId(properties.supplierId as Long)
        .foodId(properties.foodId as Long)
        .amountOfFood(properties.amountOfFood as Integer)
        .channelId(properties.channelId as Long)
        .build()
  }

  static NewPurchaserDto newPurchaser(long supplierId, long channelId) {
    return new NewPurchaserDto(supplierId, channelId)
  }

}