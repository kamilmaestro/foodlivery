package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto

trait SampleOrders {

  private static final Map NEW_Order_DEFAULT_VALUES = [
      "supplierId" : "12345",
      "foodId" : "12345",
      "amountOfFood" : "1"
  ]

  static AddProposalDto newProposal(Map<String, Object> properties = [:]) {
    properties = NEW_Order_DEFAULT_VALUES + properties

    return AddProposalDto.builder()
        .supplierId(properties.supplierId as Long)
        .foodId(properties.foodId as Long)
        .amountOfFood(properties.amountOfFood as Integer)
        .build()
  }

}