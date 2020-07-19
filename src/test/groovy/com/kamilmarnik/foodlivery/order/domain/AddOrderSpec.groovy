package com.kamilmarnik.foodlivery.order.domain

import com.kamilmarnik.foodlivery.order.dto.ProposalDto
import spock.lang.Specification

class AddOrderSpec extends Specification {

  private final OrderFacade orderFacade = new OrderConfiguration().orderFacade()

  def "should be able to create a new proposal" () {
    when: "creates a new proposal"
      ProposalDto proposal = orderFacade.createProposal()
    then: "proposal is added"
      proposal.proposalId == orderFacade.getProposal(proposal.getProposalId()).proposalId
  }

}
