package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.SecurityContextProvider
import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import com.kamilmarnik.foodlivery.samples.SampleFood
import com.kamilmarnik.foodlivery.samples.SampleOrders
import com.kamilmarnik.foodlivery.samples.SampleSuppliers
import com.kamilmarnik.foodlivery.samples.SampleUsers
import com.kamilmarnik.foodlivery.supplier.domain.SupplierConfiguration
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto
import com.kamilmarnik.foodlivery.supplier.dto.SupplierDto
import com.kamilmarnik.foodlivery.user.dto.UserDto
import spock.lang.Specification

abstract class BaseOrderSpec extends Specification implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SecurityContextProvider {

  SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()
  OrderFacade orderFacade = new OrderConfiguration().orderFacade(supplierFacade)

  def setup() {
    logInUser(JOHN)
  }

  ProposalDto addProposal(String supplierName) {
    SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: supplierName))
    return addProposalForSupplier(supplier.id)
  }

  ProposalDto addProposal(String supplierName, UserDto createdBy) {
    logInUser(createdBy)
    return addProposal(supplierName)
  }

  ProposalDto addProposal(long supplierId, UserDto createdBy) {
    logInUser(createdBy)
    return addProposalForSupplier(supplierId)
  }

  private ProposalDto addProposalForSupplier(long supplierId) {
    FoodDto food = supplierFacade.addFoodToSupplierMenu(newFood(supplierId: supplierId))
    return orderFacade.createProposal(newProposal(supplierId: supplierId, foodId: food.id))
  }

}
