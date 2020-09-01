package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto

trait SampleOrders {

  static AddProposalDto newProposal(Long supplierId, Long foodId) {
    return AddProposalDto.builder()
      .supplierId(supplierId)
      .foodId(foodId)
      .build()
  }

}