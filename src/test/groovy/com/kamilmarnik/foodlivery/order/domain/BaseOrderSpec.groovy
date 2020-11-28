package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.SecurityContextProvider
import com.kamilmarnik.foodlivery.channel.domain.BaseChannelSpec
import com.kamilmarnik.foodlivery.order.dto.AcceptedOrderDto
import com.kamilmarnik.foodlivery.order.dto.FinalizedOrderDto
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.samples.SampleChannels
import com.kamilmarnik.foodlivery.samples.SampleFood
import com.kamilmarnik.foodlivery.samples.SampleOrders
import com.kamilmarnik.foodlivery.samples.SampleSuppliers
import com.kamilmarnik.foodlivery.samples.SampleUsers
import com.kamilmarnik.foodlivery.supplier.domain.SupplierConfiguration
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto

abstract class BaseOrderSpec extends BaseChannelSpec implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SampleChannels, SecurityContextProvider {

  SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()
  OrderFacade orderFacade = new OrderConfiguration().orderFacade(supplierFacade)

  static Long CHANNEL_ID = null

  def setup() {
    logInUser(JOHN)
    CHANNEL_ID = channelFacade.createChannel(KRAKOW.name).id
  }

  FinalizedOrderDto newFinalizedOrder(String supplierName) {
    ProposalDto proposal = addProposal(supplierName)
    AcceptedOrderDto order = orderFacade.becomePurchaser(newPurchaser(proposal.supplierId, proposal.channelId))

    return orderFacade.finalizeOrder(order.id)
  }

  ProposalDto addProposal(String supplierName) {
    return addProposal(supplierName, CHANNEL_ID)
  }

  ProposalDto addProposal(long supplierId) {
    return addProposalForSupplier(supplierId, CHANNEL_ID)
  }

  ProposalDto addProposal(long supplierId, long channelId) {
    return addProposalForSupplier(supplierId, channelId)
  }

  ProposalDto addProposal(String supplierName, long channelId) {
    SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: supplierName))
    return addProposalForSupplier(supplier.id, channelId)
  }

  private ProposalDto addProposalForSupplier(long supplierId, long channelId) {
    FoodDto food = supplierFacade.addFoodToSupplierMenu(newFood(supplierId: supplierId))
    return orderFacade.createProposal(newProposal(supplierId: supplierId, foodId: food.id, channelId: channelId))
  }

}
