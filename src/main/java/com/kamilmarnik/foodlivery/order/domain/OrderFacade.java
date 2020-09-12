package com.kamilmarnik.foodlivery.order.domain;

import com.kamilmarnik.foodlivery.order.dto.AddProposalDto;
import com.kamilmarnik.foodlivery.order.dto.ProposalDto;
import com.kamilmarnik.foodlivery.order.exception.ProposalNotFound;
import com.kamilmarnik.foodlivery.supplier.domain.SupplierFacade;
import com.kamilmarnik.foodlivery.supplier.dto.FoodDto;
import com.kamilmarnik.foodlivery.supplier.exception.FoodNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderFacade {

  OrderRepository orderRepository;
  OrderCreator orderCreator;
  SupplierFacade supplierFacade;

  public ProposalDto createProposal(AddProposalDto addProposal) {
    final FoodDto foodDto = supplierFacade.getSupplierMenu(addProposal.getSupplierId()).getMenu().stream()
        .filter(food -> food.getId() == addProposal.getFoodId())
        .findFirst()
        .orElseThrow(() -> new FoodNotFound("Can not find food with id: " + addProposal.getFoodId()));
    final Order proposal = orderCreator.createProposal(addProposal);

    return orderRepository.save(proposal).dto(foodDto);
  }

}
