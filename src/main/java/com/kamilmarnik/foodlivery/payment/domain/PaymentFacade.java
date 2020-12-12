package com.kamilmarnik.foodlivery.payment.domain;

import com.kamilmarnik.foodlivery.infrastructure.PageInfo;
import com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter;
import com.kamilmarnik.foodlivery.order.event.OrderFinished;
import com.kamilmarnik.foodlivery.payment.dto.PaymentDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.kamilmarnik.foodlivery.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentFacade {

  PaymentRepository paymentRepository;
  PaymentCreator paymentCreator;

  @EventListener
  void onOrderFinished(OrderFinished orderFinished) {
    final Set<Payment> payment = paymentCreator.createPayments(orderFinished);
    paymentRepository.saveAll(payment);
  }

  public Page<PaymentDto> findUserCharges(PageInfo pageInfo) {
    return paymentRepository.findAllByPayerId(getLoggedUserId(), pageInfo.toPageRequest()).map(Payment::dto);
  }

  public Page<PaymentDto> findUserMoneyDue(PageInfo pageInfo) {
    return paymentRepository.findAllByPurchaserId(getLoggedUserId(), pageInfo.toPageRequest()).map(Payment::dto);
  }

}
