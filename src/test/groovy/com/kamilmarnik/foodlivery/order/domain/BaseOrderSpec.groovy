package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.SecurityContextProvider
import com.kamilmarnik.foodlivery.channel.domain.BaseChannelSpec
import com.kamilmarnik.foodlivery.channel.dto.ChannelDto
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

  def setup() {
    logInUser(JOHN)
  }

  ProposalDto addProposal(String supplierName) {
    return addProposal(supplierName, createChannel().id)
  }

  ProposalDto addProposal(long supplierId) {
    return addProposalForSupplier(supplierId, createChannel().id)
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

  private ChannelDto createChannel() {
    return channelFacade.createChannel(newChannel())
  }

}
