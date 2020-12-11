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
import com.kamilmarnik.foodlivery.utils.FixedTimeProvider
import com.kamilmarnik.foodlivery.utils.TimeProvider
import org.springframework.context.ApplicationEventPublisher

abstract class BaseOrderSpec extends BaseChannelSpec implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SampleChannels, SecurityContextProvider {

  ApplicationEventPublisher eventPublisher = Mock()
  SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()
  OrderExpirationConfig expirationConfig = new OrderExpirationConfig()
  TimeProvider timeProvider = new FixedTimeProvider()
  OrderFacade orderFacade = new OrderConfiguration().orderFacade(supplierFacade, expirationConfig, timeProvider, eventPublisher)

  static Long CHANNEL_ID = null

  def setup() {
    expirationConfig.setExpirationAfterMinutes(180)
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
